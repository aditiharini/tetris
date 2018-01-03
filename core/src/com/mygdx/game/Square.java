package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by aditisri on 12/30/17.
 */
public class Square {
    private float unitSize;
    private Sprite fill;
    private Rectangle bound;
    public Square(TetrisColor color, float x, float y, float unitSize, float scale){
        this.unitSize = unitSize;
        this.fill = new Sprite(TextureFactory.getTexture(color));
        this.fill.setX(x);
        this.fill.setY(y);
        this.fill.setScale(scale);
        this.bound = new Rectangle(x, y, unitSize, unitSize);
        this.alignFillWithBounds();

    }

    public float getX(){
        return this.bound.x;
    }

    public float getY(){
        return this.bound.y;
    }

    public void rotate(boolean clockwise){
        if(clockwise) {
            this.fill.rotate(90);
        }
        else{
            this.fill.rotate(-90);
        }
    }

    public void setOrigin(float x, float y){
        this.fill.setOrigin(x, y);
    }

    public void alignFillWithBounds(){
        this.fill.translateX(this.bound.x - this.fill.getBoundingRectangle().x);
        this.fill.translateY(this.bound.y - this.fill.getBoundingRectangle().y);
    }

    public void updateUp(float distance){
        System.out.print("got to update up " + this.bound.x + " " + this.bound.y);
        this.bound.y += distance;
        this.fill.translateY(distance);
        System.out.println(" after " + this.bound.x + " " + this.bound.y);
    }

    public void updateDown(float distance){
        System.out.print("got to update down " + this.bound.x + " " + this.bound.y);
        this.bound.y -= distance;
        this.fill.translateY(-distance);
        System.out.println(" after position " + this.bound.x + " " + this.bound.y);
    }

    public void updateRight(float distance){
        System.out.println("got to update right");
        this.bound.x += distance;
        this.fill.translateX(distance);
    }

    public void updateLeft(float distance){
        System.out.println("got to update left");
        this.bound.x -= distance;
        this.fill.translateX(-distance);
    }

    public boolean isInBounds(){
        return this.bound.y >= 0 && this.bound.x >= 0 && this.bound.x + unitSize <= Gdx.graphics.getWidth();
    }


    public boolean hitBottom(){
        return this.bound.y < 0;
    }

    public boolean isRestingOn(Square s){
        return this.bound.y == s.getY() + unitSize && this.getCol() == s.getCol();

    }

    public boolean isOverflowing(){
        return this.bound.y + unitSize > Gdx.graphics.getHeight();
    }

    public void setPosition(int row, int col){
        float x = col*unitSize;
        float y = row*unitSize;
        this.fill.setPosition(x, y);
        this.setBound(x, y);
        alignFillWithBounds();
    }

    public void setBound(float x, float y){
        this.bound.setPosition(x, y);
    }

    public boolean collidesWith(Square s){
        return this.bound.overlaps(s.getBound());
    }


    public int getRow(){
        return (int)(this.fill.getBoundingRectangle().y/unitSize);
    }

    public int getCol(){
        return (int)(this.fill.getBoundingRectangle().x/unitSize);
    }

    public Rectangle getBound(){
        return this.bound;
    }

    public void draw(SpriteBatch batch){
        this.fill.draw(batch);
    }

    public void drawBound(SpriteBatch batch){
        Pixmap p = new Pixmap((int)this.bound.getWidth(), (int)this.bound.getHeight(), Pixmap.Format.RGBA8888);
        p.setColor(Color.CYAN);
        p.fill();
        Texture t = new Texture(p);
        batch.draw(t, this.bound.getX(), this.bound.getY());
    }




}
