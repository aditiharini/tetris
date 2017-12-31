package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import java.util.*;

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

    public void drawBackground(){
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

    public void draw(SpriteBatch batch) {
        for(Set<Square> squareSet : this.rows.values()){
            for(Square s : squareSet){
                s.draw(batch);
//                s.drawBound(batch);
            }
        }
    }

    public void addPiece(Tetromino t) {
        for (Square s: this.getSquaresFromBounds(t.bounds, t.fill.getBoundingRectangle())){
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

    public void printRowCounts(){
        System.out.println("============");
        for(int row : this.rows.keySet()){
            System.out.println(row + " count " + this.rows.get(row).size());
        }
        System.out.println("============");
    }

    public Set<Square> getRelevantSquares(int row, int col){
        Set<Square> relevantSquares = new HashSet<Square>();
        for (Set<Square> squareSet : this.rows.values()){
            relevantSquares.addAll(squareSet);
        }
//        for (int i = row; i<= row+4; i++){
//            if(!this.rows.containsKey(i)){
//                continue;
//            }
//            Set<Square> s1 = this.rows.get(i);
//            for (int j = col; j<= row+4; j++){
//                if(!this.columns.containsKey(j)){
//                    continue;
//                }
//                Set<Square> s2 = new HashSet<Square>(s1);
//                s2.retainAll(this.columns.get(j));
//                relevantSquares.addAll(s2);
//            }
//        }
        return relevantSquares;
    }

    public void handleDeletions(){
        List<Integer> fullRows = new ArrayList<Integer>();
        for(int i : this.rows.keySet()){
            if(isRowFull(i)){
                fullRows.add(i);
            }
        }

        for (int i : fullRows){
            for(int j=i; j+1<this.getNumRows(); j++){
                if (this.rows.containsKey(j+1)){
                    for(Square s: this.rows.get(j+1)){
                        s.updateDown();
                    }
                    this.rows.put(j, this.rows.get(j+1));
                    this.rows.remove(j+1);
                }
            }
        }
        this.printRowCounts();
    }

    public boolean isRowFull(int row){
        if(!this.rows.containsKey(row)){
            return false;
        }
        return this.rows.get(row).size() == this.getNumCols();
    }

    public Set<Square> getSquaresFromBounds(Bounds b, Rectangle fill){
        Set<Square> squares = new HashSet<Square>();
        Rectangle[] rectangles = {b.getHorizontalBound(), b.getVerticalBound()};
//        Rectangle[] rectangles = {fill};
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
