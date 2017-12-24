package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by aditisri on 12/21/17.
 */
public class ShapeFactory {
    public static Sprite getShape(Shape s){
        switch(s){
            case I:
                return new Sprite(new Texture("../assets/I/pink_bevelled_I.png"));
            case L:
                return new Sprite(new Texture("../assets/L/pink_bevelled_L.png"));
            case T:
                return new Sprite(new Texture("../assets/T/pink_bevelled_T.png"));
            case S:
                return new Sprite(new Texture("../assets/S/pink_bevelled_S.png"));
            case R:
                return new Sprite(new Texture("../assets/R/pink_bevelled_R.png"));
        }
        return null;
    }

    public static Sprite getRandomShape(){
        Shape s = Shape.values()[(int)(Shape.values().length * Math.random())];
        return getShape(s);
    }

}
