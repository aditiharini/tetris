package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by aditisri on 12/25/17.
 */
public class Bounds {
    private Rectangle vertical;
    private Rectangle horizontal;
    private Orientation orientation;
    private Shape shape;
    private float unitSize;
    public Bounds(Shape s, Orientation o , float unitSize, float startx, float starty){
        this.shape = s;
        this.orientation = o;
        this.unitSize = unitSize;
        this.vertical = BoundFactory.getVerticalBound(this.shape, this.orientation, this.unitSize, startx, starty);
        this.horizontal = BoundFactory.getHorizontalBound(this.shape, this.orientation, this.unitSize, startx, starty);
    }

    public Rectangle getVerticalBound(){
        return this.vertical;
    }

    public Rectangle getHorizontalBound(){
        return this.horizontal;
    }

    public Shape getShape(){
        return this.shape;
    }

    public Orientation getOrientation(){
        return this.orientation;
    }

    public float getMinX(){
        return Math.min(vertical.x, horizontal.x);
    }

    public float getMinY(){
        return Math.min(vertical.y, horizontal.y);
    }

    public float getMaxY(){
        return Math.max(vertical.y, horizontal.y);
    }

    public float getMaxX(){
        return Math.max(vertical.x, horizontal.x);
    }


    public void rotate(boolean clockwise, float x, float y){
        if(clockwise)
            this.orientation = this.orientation.getNextClockwise();
        else
            this.orientation = this.orientation.getNextCounterClockwise();

        this.vertical = BoundFactory.getVerticalBound(this.shape, this.orientation, this.unitSize, x, y);
        this.horizontal = BoundFactory.getHorizontalBound(this.shape, this.orientation, this.unitSize, x, y);
    }

    public boolean overlaps(Bounds other){
        return this.vertical.overlaps(other.vertical) ||
                this.vertical.overlaps(other.horizontal) ||
                this.horizontal.overlaps(other.horizontal) ||
                this.horizontal.overlaps(other.vertical);
    }

    public void updateDown(float distance){
        this.horizontal.y -= distance;
        this.vertical.y -= distance;
    }

    public void updateLeft(float distance){
        this.horizontal.x -= distance;
        this.vertical.x -= distance;
    }

    public void updateRight(float distance){
        this.horizontal.x += distance;
        this.vertical.x += distance;
    }

    public void updateUp(float distance){
        this.horizontal.y += distance;
        this.vertical.y += distance;
    }

    public boolean isInBounds(){
        return getMinX() >= 0 && getMinY() >= 0 && getMaxX() < Gdx.graphics.getWidth();
    }

    public void snapToGrid(){
        updateUp(getMinY()%unitSize);
        updateRight(getMinX()%unitSize);
    }

    public float getYTranslateDistance(float destY){
        return destY - getMinY();
    }

    public float getXTranslateDistance(float destX){
        return destX - getMinX();

    }

    public float getWidth(){
        return getMaxX() - getMinX();
    }

    public float getHeight(){
        return getMaxY() - getMinY();
    }


    public void snapInBounds(){
        if (this.getMinY() < 0)
            updateUp(getYTranslateDistance(0));
        if (this.getMinX() < 0)
            updateRight(getXTranslateDistance(0));
        if (this.getMaxX() > Gdx.graphics.getWidth()-getWidth())
            updateRight(getXTranslateDistance(Gdx.graphics.getWidth()-getWidth()));
    }

    public void printBounds(){
        System.out.println("bound1 " + this.vertical.getX() + " " + this.vertical.getY() + " " + this.vertical.getHeight() + " " + this.vertical.getWidth());
        System.out.println("bound2 " + this.horizontal.getX() + " " + this.horizontal.getY() + " " + this.horizontal.getHeight() + " " + this.horizontal.getWidth());

    }

    public void drawBounds(SpriteBatch batch){
        Pixmap p1 = new Pixmap((int)this.vertical.getWidth(), (int)this.vertical.getHeight(), Pixmap.Format.RGBA8888);
        p1.setColor(Color.WHITE);
        p1.fill();
        Pixmap p2 = new Pixmap((int)this.horizontal.getWidth(), (int)this.horizontal.getHeight(), Pixmap.Format.RGBA8888);
        p2.setColor(Color.WHITE);
        p2.fill();
        batch.draw(new Texture(p1), this.vertical.getX(), this.vertical.getY());
        batch.draw(new Texture(p2), this.horizontal.getX(), this.horizontal.getY());
//        p1.drawRectangle((int)this.vertical.getX(), (int)this.vertical.getY(), (int)this.vertical.getWidth(), (int)this.vertical.getHeight());
//        p2.drawRectangle((int)this.horizontal.getX(), (int)this.horizontal.getY(), (int)this.horizontal.getWidth(), (int)this.horizontal.getHeight());




    }



}
