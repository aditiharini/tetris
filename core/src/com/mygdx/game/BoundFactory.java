package com.mygdx.game;

import com.badlogic.gdx.math.Rectangle;
import org.w3c.dom.css.Rect;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aditisri on 12/23/17.
 */
public class BoundFactory {
    public static List<Rectangle> getBounds(Shape s, float unitSize, float startx, float starty){
        List<Rectangle> bounds = new ArrayList<Rectangle>();
        switch(s){
            case I:
                bounds.add(new Rectangle(startx, starty, unitSize, 4*unitSize));
                break;
            case L:
                bounds.add(new Rectangle(startx, starty, unitSize, 3*unitSize));
                bounds.add(new Rectangle(startx + unitSize, starty, unitSize, unitSize));
                break;
            case T:
                bounds.add(new Rectangle(startx, starty, unitSize*3, unitSize));
                bounds.add(new Rectangle(startx + unitSize, starty + unitSize, unitSize, unitSize));
                break;
            case R:
                bounds.add(new Rectangle(startx, starty, unitSize*2, unitSize*2));
                break;
            case S:
                bounds.add(new Rectangle(startx, starty, unitSize*2, unitSize));
                bounds.add(new Rectangle(startx + unitSize, starty + unitSize, unitSize*2, unitSize));
                break;
        }
        return bounds;
    }
}
