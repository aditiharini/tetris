package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import org.w3c.dom.css.Rect;

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
    public Sprite fill;
    private int height;
    private int width;
    private float speed;
    private boolean isFalling;
    private boolean shouldMoveDown;
    public Lock updateLock;
    public Move currentMove;
    public Bounds bounds;
    public Shape shape;



    public Tetromino(float startx, float starty, Color color){
        this.shape = Shape.values()[(int)(Shape.values().length * Math.random())];
        this.fill = this.createTexture(startx, starty, null);

        this.shouldMoveDown = false;
        this.bounds = new Bounds(this.shape, Orientation.UP, unitSize, this.fill.getBoundingRectangle().x, this.fill.getBoundingRectangle().y);
//        this.height = (int)this.getBound().getHeight();
//        this.width = (int)this.getBound().getWidth();
        this.speed = unitSize/1000f;
        this.isFalling = true;
        Thread t = new Thread(new UpdateTimer(this));
        t.start();
        updateLock = new ReentrantLock();
        currentMove = null;
    }

    public void move(Move m){
        updateLock.lock();
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
        updateLock.unlock();
    }

    public void fixCollisions(Grid grid, List<Tetromino> allPieces){
        Set<Square> possibleCollisions = grid.getRelevantSquares((int)(this.bounds.getMinX()/unitSize), (int)(this.bounds.getMinY()/unitSize));
        for(Square s: possibleCollisions){
            if(this.bounds.overlaps(s.getBound())){
                this.handleCollision(s);
            }
        }

//        for(Tetromino t : allPieces){
//            if (this.collidesWith(t))
//                this.handleCollision();
//        }
    }



    public void setShouldMoveDown(boolean shouldMove){
        this.shouldMoveDown = shouldMove;
    }

    public void rotateCounterClockwise(){
        this.currentMove = Move.TURN_LEFT;
        this.fill.rotate(-90);
        this.bounds.rotate(false, this.fill.getBoundingRectangle().x, this.fill.getBoundingRectangle().y);
        snapInBounds();
        snapToGrid();
//        System.out.println("fill " + this.fill.getBoundingRectangle().x + " " + this.fill.getBoundingRectangle().y);
//        System.out.println("bounds " + this.bounds.getMinX() + " " + this.bounds.getMinY());
    }



    public void rotateClockwise(){
        this.currentMove = Move.TURN_RIGHT;
        this.fill.rotate(90);
        this.bounds.rotate(true, this.fill.getBoundingRectangle().x, this.fill.getBoundingRectangle().y);
        snapInBounds();
        snapToGrid();
//        System.out.println("fill " + this.fill.getBoundingRectangle().x + " " + this.fill.getBoundingRectangle().y);
//        System.out.println("bounds " + this.bounds.getMinX() + " " + this.bounds.getMinY());
    }

    public void updateRight(){
        this.currentMove = Move.RIGHT;
        this.fill.translateX(getStepDistance());
        this.bounds.updateRight(getStepDistance());
        snapInBounds();
        snapToGrid();
    }

    public float getStepDistance(){
        return speed*timestep;
    }

    public void updateLeft(){
        this.currentMove = Move.LEFT;
        this.fill.translateX(-getStepDistance());
        this.bounds.updateLeft(getStepDistance());
        snapInBounds();
        snapToGrid();

    }

    public void undoPrevMove(){
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
        this.currentMove = Move.DOWN;
        this.fill.translateY(-getStepDistance());
        this.bounds.updateDown(getStepDistance());
        snapInBounds();
        snapToGrid();
        this.setShouldMoveDown(false);
    }

    public void updateUp(){
        this.fill.translateY(getStepDistance());
        this.bounds.updateUp(getStepDistance());
        snapInBounds();
        snapToGrid();
    }

    public void snapToGrid(){
        this.bounds.snapToGrid();
        this.fill.translateY(this.fill.getBoundingRectangle().getY()%unitSize);
        this.fill.translateX(this.fill.getBoundingRectangle().getX()%unitSize);
    }


    public void snapInBounds(){
        this.bounds.snapInBounds();
        if(this.fill.getBoundingRectangle().getY() < 0){
            this.isFalling = false;
            this.fill.translateY(getStepDistance());
        }
        if (this.fill.getBoundingRectangle().getX() < 0) {
            this.fill.translateX(-this.fill.getBoundingRectangle().getX());
        }
        if(this.fill.getBoundingRectangle().getX() +this.fill.getBoundingRectangle().width > Gdx.graphics.getWidth()){
            this.fill.translateX(-getStepDistance());
//            this.fill.translateX(Gdx.graphics.getWidth()-(this.fill.getBoundingRectangle().getX()+this.fill.getBoundingRectangle().width));
        }

    }

    public boolean isRestingOn(Square s){
        this.updateDown();
        boolean overlaps = this.bounds.overlaps(s.getBound());
        this.updateUp();
        return overlaps;
    }

    public boolean collidesWith(Tetromino other){
       return this.bounds.overlaps(other.bounds);
    }

    public void handleCollision(Square s){
        this.undoPrevMove();
        if(this.bounds.getMaxY() > Gdx.graphics.getHeight()){
            System.out.println("game over");
            System.exit(1);
        }
        if(this.isRestingOn(s)) {
            this.isFalling = false;
        }

    }

    public boolean isFalling(){
        return this.isFalling;
    }

    public Sprite createTexture(float startx, float starty, Color color){
        Sprite fill = ShapeFactory.getShape(this.shape);
        fill.setX(startx);
        fill.setY(starty);
        fill.setScale(scale, scale);
        return fill;
    }


    public void draw(SpriteBatch batch){
        this.fill.draw(batch);
        this.bounds.drawBounds(batch);

    }

}
