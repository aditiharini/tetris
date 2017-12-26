package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by aditisri on 12/2/17.
 */
public class Grid {
    private float unitSize;
    public Grid(float unitSize){
        this.unitSize = unitSize;
    }

    public void draw(){
        ShapeRenderer drawingTool = new ShapeRenderer();
        drawingTool.begin(ShapeRenderer.ShapeType.Line);
        drawingTool.setColor(Color.WHITE);
        for(float i = 0; i< Gdx.graphics.getWidth(); i+= unitSize){
            drawingTool.line(i, 0, i, Gdx.graphics.getHeight());
        }

        for(float i = 0; i< Gdx.graphics.getHeight(); i+= unitSize){
            drawingTool.line(0, i, Gdx.graphics.getWidth(), i);
        }
        drawingTool.end();

    }
}
