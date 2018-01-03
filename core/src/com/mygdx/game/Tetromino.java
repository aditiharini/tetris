package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by aditisri on 11/24/17.
 */
public class Tetromino {
    public static float timestep = 1000;
    public static float scale = 0.5f;
    public static float unitSize = 40f*scale;
    private float speed;
    private boolean isFalling;
    private boolean shouldMoveDown;
    public Lock updateLock;
    public Move currentMove;
    public Shape shape;
    private UpdateTimer timer;
    private boolean isGameOver;
    private Set<Square> squares;
    private Orientation orientation;
    private TetrisColor color;



    public Tetromino(float startx, float starty, Color color){
        this.shape = Shape.values()[(int)(Shape.values().length * Math.random())];
        this.color = TetrisColor.values()[(int)(TetrisColor.values().length * Math.random())];
        this.squares = TetrominoFactory.getTetromino(this.shape, this.color, startx, starty, unitSize);

        this.shouldMoveDown = false;
        this.speed = (unitSize/timestep);
        this.isFalling = true;
        this.timer = new UpdateTimer(this);
        Thread t = new Thread(this.timer);
        t.start();
        updateLock = new ReentrantLock();
        currentMove = null;
        isGameOver = false;
        this.orientation = Orientation.UP;
    }

    public float getMinX(){
        float minX = Float.MAX_VALUE;
        for (Square s: this.squares){
            Math.min(s.getX(), minX);
        }
        return minX;
    }

    public float getMinY(){
        float minY = Float.MIN_VALUE;
        for(Square s: this.squares){
            Math.min(s.getY(), minY);
        }
        return minY;
    }

    public int getMinCol(){
        int col = Integer.MAX_VALUE;
        for(Square s : this.squares){
            col = Math.min(s.getCol(), col);
        }
        return col;
    }

    public int getMinRow(){
        int row = Integer.MAX_VALUE;
        for(Square s : this.squares){
            row = Math.min(s.getRow(), row);
        }
        return row;
    }

    public int getMaxRow(){
        int row = Integer.MIN_VALUE;
        for(Square s : this.squares){
            row = Math.max(s.getRow(), row);
        }
        return row;
    }

    public int getMaxCol(){
        int col = Integer.MIN_VALUE;
        for (Square s : this.squares){
            col = Math.max(s.getCol(), col);
        }
        return col;
    }



    public void move(Move m){
        updateLock.lock();
        if(!isFalling()){
            return;
        }
        this.currentMove = m;
        switch(m){
            case LEFT:
                updateLeft();
                break;
            case RIGHT:
                updateRight();
                break;
            case UP:
                updateUp();
                break;
            case DOWN:
                if(shouldMoveDown)
                    updateDown();
                break;
            case TURN_RIGHT:
                rotateClockwise();
                break;
            case TURN_LEFT:
                rotateCounterClockwise();
                break;
        }
        this.fixCollisions(TetrisGame.grid, TetrisGame.landedPieces);
        if (!this.timer.isRunning()){
            this.timer.resume();
            this.timer = new UpdateTimer(this);
            Thread t = new Thread(this.timer);
            t.start();
        }
        updateLock.unlock();
    }

    public Set<Square> getSquares(){
        return this.squares;
    }

    public void fixCollisions(Grid grid, List<Tetromino> allPieces){
        Set<Square> possibleCollisions = grid.getRelevantSquares(0, 0);
        for(Square s1: possibleCollisions){
            for(Square s2 : this.squares){
                if(s2.isRestingOn(s1)){
                    this.isFalling = false;
                }
                if(s1.collidesWith(s2)){
                    this.handleCollision(s1);
                }
            }
        }
    }



    public void setShouldMoveDown(boolean shouldMove){
        this.shouldMoveDown = shouldMove;
    }


    public void rotateCounterClockwise(){
        this.currentMove = Move.TURN_LEFT;
        int maxCol = getMaxCol();
        int minRow = getMinRow();
        int minCol = getMinCol();
        for(Square s : squares){
            int colOffset = s.getRow()-minRow;
            int rowOffset = s.getCol()-minCol;
            s.setPosition(minRow + rowOffset, maxCol - colOffset);
        }
        this.orientation = this.orientation.getNextCounterClockwise();
        snapInBounds();
    }



    public void rotateClockwise(){
        this.currentMove = Move.TURN_RIGHT;
        int maxRow = getMaxRow();
        int maxCol = getMaxCol();
        int minRow = getMinRow();
        for(Square s : squares){
            int colOffset = maxRow - s.getRow();
            int rowOffset = maxCol - s.getCol();
            s.setPosition(minRow + rowOffset, maxCol - colOffset);
        }
        this.orientation = this.orientation.getNextClockwise();
        snapInBounds();
    }

    public void updateRight(){
        System.out.println("got to update right");
        this.currentMove = Move.RIGHT;
        for(Square s: this.squares){
            s.updateRight(getStepDistance());
        }
        snapInBounds();
    }

    public float getStepDistance(){
        return speed*timestep;
    }

    public void updateLeft(){
        this.currentMove = Move.LEFT;
        for(Square s: this.squares){
            s.updateLeft(getStepDistance());
        }
        snapInBounds();

    }

    public void undoPrevMove(){
        System.out.println("got to undo");
        switch(this.currentMove){
            case LEFT:
                this.updateRight();
                break;
            case RIGHT:
                this.updateLeft();
                break;
            case UP:
                this.updateDown();
                break;
            case DOWN:
                System.out.println("got to undo down");
                this.updateUp();
                break;
            case TURN_RIGHT:
                this.rotateCounterClockwise();
                break;
            case TURN_LEFT:
                this.rotateClockwise();
                break;
        }
    }


    public void updateDown(){
        System.out.println("got to update down");
        this.currentMove = Move.DOWN;
        for(Square s: this.squares){
            s.updateDown(getStepDistance());
        }
        snapInBounds();
        this.setShouldMoveDown(false);
    }

    public void updateUp(){
        for(Square s: this.squares){
            s.updateUp(getStepDistance());
        }
        snapInBounds();
    }



    public boolean isInBounds(){
        for(Square s : this.squares){
            if (!s.isInBounds()){
                return false;
            }
        }
        return true;
    }

    public boolean hitBottom(){
        for(Square s : this.squares){
            if(s.hitBottom()){
                return true;
            }
        }
        return false;
    }


    public void snapInBounds() {
        if (!isInBounds()) {
            if (hitBottom()) {
                this.isFalling = false;
            }
            this.undoPrevMove();
        }
    }

    public boolean isRestingOn(Square s){

        for(Square s1 : this.squares){
            if (s1.isRestingOn(s)){
                return true;
            }
        }
        return false;
    }

    public boolean isOverflowing(){
        for(Square s : this.squares){
            if(s.isOverflowing()){
                return true;
            }
        }
        return false;
    }



    public void handleCollision(Square s){
        this.undoPrevMove();
        if(this.isOverflowing()){
            System.out.println("game over");
            isGameOver = true;
        }
        if(this.isRestingOn(s)) {
            this.isFalling = false;
        }

    }

    public boolean isGameOver(){
        return isGameOver;
    }

    public boolean isFalling(){
        return this.isFalling;
    }


    public void draw(SpriteBatch batch){
        for(Square s: squares){
            s.draw(batch);
        }

    }

}
