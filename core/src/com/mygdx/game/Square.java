package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by aditisri on 12/30/17.
 */
public class Square {
    private float x;
    private float y;
    private float unitSize;
    private Sprite fill;
    public Square(float x, float y, float unitSize, float scale){
        this.x = x;
        this.y = y;
        this.unitSize = unitSize;
        this.fill = new Sprite(new Texture("../assets/Squares/pink_bevelled.png"));
        this.fill.setScale(scale);
        this.fill.setPosition(x, y);
    }

    public int getRow(){
        return (int)(this.y/unitSize);
    }

    public int getCol(){
        return (int)(this.x/unitSize);
    }




}
