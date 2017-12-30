package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by aditisri on 12/2/17.
 */
public class Grid {
    private float unitSize;
    private Map<Integer, Set<Square>> rows;
    private Map<Integer, Set<Square>> columns;

    public Grid(float unitSize) {
        rows = new HashMap<Integer, Set<Square>>();
        columns = new HashMap<Integer, Set<Square>>();
        this.unitSize = unitSize;
    }

    public int getNumCols() {
        return (int) (Gdx.graphics.getWidth() / unitSize);
    }

    public int getNumRows() {
        return (int) (Gdx.graphics.getHeight() / unitSize);
    }

    public void draw() {
        ShapeRenderer drawingTool = new ShapeRenderer();
        drawingTool.begin(ShapeRenderer.ShapeType.Line);
        drawingTool.setColor(Color.WHITE);
        for (float i = 0; i < Gdx.graphics.getWidth(); i += unitSize) {
            drawingTool.line(i, 0, i, Gdx.graphics.getHeight());
        }

        for (float i = 0; i < Gdx.graphics.getHeight(); i += unitSize) {
            drawingTool.line(0, i, Gdx.graphics.getWidth(), i);
        }
        drawingTool.end();

    }

    public void addPiece(Tetromino t) {
        for (Square s: this.getSquaresFromBounds(t.bounds)){
            if(!rows.containsKey(s.getRow())){
                rows.put(s.getRow(), new HashSet<Square>());
            }
            if(!columns.containsKey(s.getCol())){
                columns.put(s.getCol(), new HashSet<Square>());
            }
            rows.get(s.getRow()).add(s);
            columns.get(s.getCol()).add(s);
        }

    }

    public void getPossibleCollisions(int row, int col){
    }


    public Set<Square> getSquaresFromBounds(Bounds b){
        Set<Square> squares = new HashSet<Square>();
        Rectangle[] rectangles = {b.getHorizontalBound(), b.getVerticalBound()};
        for (Rectangle r: rectangles) {
            for (float i = r.x; i < r.x + r.getWidth(); i += unitSize) {
                for (float j = r.y; j < r.y + r.getHeight(); j += unitSize) {
                    squares.add(new Square(i, j, unitSize, 0.5f));
                }
            }
        }
        return squares;

    }


}
