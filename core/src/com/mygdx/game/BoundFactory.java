package com.mygdx.game;

import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aditisri on 12/23/17.
 */
public class BoundFactory {

    public static Rectangle getVerticalBound(Shape s, Orientation o, float unitSize, float startx, float starty){
        switch(s){
            case I:
                if(o == Orientation.UP || o == Orientation.DOWN){
                    return new Rectangle(startx, starty, unitSize, 4*unitSize);
                }
                else{
                    return new Rectangle(startx, starty, 0, 0);
                }
            case L:
                switch(o){
                    case UP:
                        return new Rectangle(startx, starty, unitSize, 3 * unitSize);
                    case RIGHT:
                        return new Rectangle(startx, starty, 3*unitSize, unitSize);
                    case DOWN:
                        return new Rectangle(startx + unitSize, starty, unitSize, 3*unitSize);
                    case LEFT:
                        return new Rectangle(startx, starty + unitSize, 3*unitSize, unitSize);
                }
            case T:
                switch(o){
                    case UP:
                        return new Rectangle(startx, starty, unitSize*3, unitSize);
                    case RIGHT:
                        return new Rectangle(startx + unitSize, starty, unitSize, 3*unitSize);
                    case DOWN:
                        return new Rectangle(startx, starty + unitSize, 3*unitSize, unitSize);
                    case LEFT:
                        return new Rectangle(startx, starty, unitSize, 3*unitSize);
                }
            case R:
                return new Rectangle(startx, starty, 0, 0);
            case S:
                if (o == Orientation.UP || o == Orientation.DOWN){
                    return new Rectangle(startx, starty, unitSize * 2, unitSize);
                }
                else{
                    return new Rectangle(startx, starty + unitSize, unitSize, unitSize*2);

                }
        }
        return null;

    }


    public static Rectangle getHorizontalBound(Shape s, Orientation o, float unitSize, float startx, float starty){
        switch(s){
            case I:
                if(o == Orientation.LEFT || o == Orientation.RIGHT)
                    return new Rectangle(startx, starty, 4*unitSize, unitSize);
                else{
                    return new Rectangle(startx, starty, 0, 0);
                }
            case L:
                switch(o){
                    case UP:
                        return new Rectangle(startx, starty, 2*unitSize, unitSize);
                    case RIGHT:
                        return new Rectangle(startx + 2*unitSize, starty , unitSize, 2*unitSize);
                    case DOWN:
                        return new Rectangle(startx, starty + 2*unitSize, 2*unitSize, unitSize);
                    case LEFT:
                        return new Rectangle(startx, starty, unitSize, 2*unitSize);
                }
            case T:
                switch (o){
                    case UP:
                        return new Rectangle(startx + unitSize, starty + unitSize, unitSize, unitSize);
                    case RIGHT:
                        return new Rectangle(startx, starty + unitSize, unitSize, unitSize);
                    case DOWN:
                        return new Rectangle(startx+unitSize, starty, unitSize, unitSize);
                    case LEFT:
                        return new Rectangle(startx+unitSize, starty+unitSize, unitSize, unitSize);
                }
            case R:
                return new Rectangle(startx, starty, unitSize*2, unitSize*2);
            case S:
                if(o == Orientation.DOWN || o == Orientation.UP) {
                    return new Rectangle(startx + unitSize, starty + unitSize, unitSize * 2, unitSize);
                }
                else if(o == Orientation.LEFT | o == Orientation.RIGHT){
                    return new Rectangle(startx + unitSize, starty, unitSize, unitSize*2);
                }
        }
        return null;
    }
}
