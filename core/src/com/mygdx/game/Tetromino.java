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

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by aditisri on 11/24/17.
 */
public class Tetromino {
    public static float timestep = 1000;
    public static float scale = 0.5f;
    public static float unitSize = 40f*scale;
    private Sprite fill;
    private int height;
    private int width;
    private float speed;
    private boolean isFalling;
    private boolean shouldMoveDown;
    public Lock updateLock;
    public Move currentMove;


    public Tetromino(float startx, float starty, Color color){
        this.fill = this.createTexture( null);
        this.height = (int)this.getBound().getHeight();
        this.width = (int)this.getBound().getWidth();
        this.speed = unitSize/1000f;
        this.isFalling = true;
        Thread t = new Thread(new UpdateTimer(this));
        t.start();
        updateLock = new ReentrantLock();
        currentMove = null;
    }

    public void setCurrentMove(Move m){
//        updateLock.lock();
        this.currentMove = m;
//        updateLock.unlock();
    }

    public void setShouldMoveDown(boolean shouldMove){
        this.shouldMoveDown = shouldMove;
    }

    public void rotateCounterClockwise(){
        this.currentMove = Move.TURN_LEFT;
        this.fill.rotate(-90);
        snapToGrid();
    }

    public void rotateClockwise(){
        this.currentMove = Move.TURN_RIGHT;
        this.fill.rotate(90);
        snapToGrid();
    }

    public void updateRight(){
        this.currentMove = Move.RIGHT;
        this.fill.setX(this.fill.getX() + (speed*timestep));
        snapToGrid();
        snapInBounds();
    }

    public void updateLeft(){
        this.currentMove = Move.LEFT;
        this.fill.setX(this.fill.getX() - (speed*timestep));
        snapToGrid();
        snapInBounds();

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
        updateLock.lock();
        this.currentMove = Move.DOWN;
        if(this.shouldMoveDown) {
            this.fill.setY(this.fill.getY() -(speed*timestep));
            if (!isInBounds()) {
                snapInBounds();
            }
            snapToGrid();
            snapInBounds();
            this.setShouldMoveDown(false);
        }
        updateLock.unlock();
    }

    public void updateUp(){
        this.fill.setY(this.fill.getY() + (speed*timestep));
        snapToGrid();
        snapInBounds();
    }

    public Rectangle getBound(){
        return this.fill.getBoundingRectangle();
    }

    public boolean isInBounds(){
        return this.getBound().getX() >= 0 && this.getBound().getX()+this.width < Gdx.graphics.getWidth() && this.getBound().getY() >= 0;
    }

    public void snapInBounds(){

        if (this.getBound().getY() < 0){
            this.isFalling = false;
            this.fill.setY(this.fill.getY() + (0-this.getBound().getY()));
        }
        if (this.getBound().getX() < 0){
            this.fill.setX(0);
        }
        if(this.getBound().getX() > Gdx.graphics.getWidth()-this.width){
            this.fill.setX(Gdx.graphics.getWidth() - this.width);
        }
    }

    public void snapToGrid(){
        if(this.getBound().getY()%unitSize != 0){

            this.fill.setY(this.fill.getY()-(this.getBound().getY()%unitSize));
        }
        if(this.getBound().getX()%unitSize != 0){

            this.fill.setX(this.fill.getX()-(this.getBound().getX()%unitSize));
        }
    }


    public boolean collidesWith(Tetromino other){
        return this.getBound().overlaps(other.getBound());
    }

    public void handleCollision(Tetromino other){
//        this.undoPrevMove();
        if (this.getBound().getY() <= other.getBound().getY() + other.getBound().getHeight()){
            this.isFalling = false;
            this.fill.setY(this.fill.getY() + (other.getBound().getY() + other.getBound().getHeight() - this.getBound().getY()));
        }
        else if(this.getBound().getX() < other.getBound().getX() + other.getBound().getWidth()){
            this.fill.setX(this.fill.getX() + (other.getBound().getX() + other.getBound().getWidth() - this.getBound().getX()));
            this.handleCollision(other);
        }
        if(this.collidesWith(other)){
            this.handleCollision(other);
        }

    }

    public boolean isFalling(){
        return this.isFalling;
    }

    public Sprite createTexture(Color color){
//        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
//        pixmap.setColor(color);
//        pixmap.fillRectangle(0, 0, width, height);
//        Texture t = new Texture(pixmap);
//        pixmap.dispose();
//        return t;
        Sprite s = ShapeFactory.getRandomShape();
        s.setX(200);
        s.setY(500);
        s.setScale(scale, scale);

        return s;
    }

    public void draw(SpriteBatch batch){
        this.fill.draw(batch);
//        batch.draw(this.fill, this.bound.x, this.bound.y);
//        Gdx.graphics.requestRendering();

    }



}
