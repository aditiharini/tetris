package com.mygdx.game;

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
    private float x;
    private float y;
    private float unitSize;
    private Sprite fill;
    private Rectangle bound;
    public Square(float x, float y, float unitSize, float scale){
        this.x = x;
        this.y = y;
        this.unitSize = unitSize;
        this.fill = new Sprite(new Texture("../assets/Squares/pink_bevelled.png"));
        this.fill.setX(x);
        this.fill.setY(y);
        this.fill.setScale(scale);
//        this.fill.setBounds(x, y, unitSize/scale, unitSize/scale);
//        this.fill.setPosition(x, y);
        this.bound = new Rectangle(x, y, unitSize, unitSize);
        this.alignFillWithBounds();

    }

    public void alignFillWithBounds(){
        this.fill.translateX(this.bound.x - this.fill.getBoundingRectangle().x);
        this.fill.translateY(this.bound.y - this.fill.getBoundingRectangle().y);
    }

    public void updateDown(){
        this.bound.y -= unitSize;
        this.fill.translateY(-unitSize);
    }

    public int getRow(){
        return (int)(this.y/unitSize);
    }

    public int getCol(){
        return (int)(this.x/unitSize);
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
