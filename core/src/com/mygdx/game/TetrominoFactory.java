package com.mygdx.game;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by aditisri on 1/1/18.
 */
public class TetrominoFactory {

    public static Set<Square> getTetromino(Shape s, TetrisColor color, float x, float y, float unitSize){
        Set<Square> squares = new HashSet<Square>();
        switch(s){
            case I:
                squares.add(new Square(color, x, y, unitSize, 0.5f));
                squares.add(new Square(color, x, y+unitSize,unitSize,  0.5f));
                squares.add(new Square(color, x, y+(2*unitSize), unitSize, 0.5f));
                squares.add(new Square(color, x, y+(3*unitSize), unitSize, 0.5f));
                break;
            case L:
                squares.add(new Square(color, x, y, unitSize, 0.5f));
                squares.add(new Square(color, x+unitSize, y, unitSize, 0.5f));
                squares.add(new Square(color, x, y+unitSize, unitSize, 0.5f));
                squares.add(new Square(color, x, y+(2*unitSize), unitSize, 0.5f));
                break;
            case T:
                squares.add(new Square(color, x, y, unitSize, 0.5f));
                squares.add(new Square(color, x+unitSize, y, unitSize, 0.5f));
                squares.add(new Square(color, x+(2*unitSize), y, unitSize, 0.5f));
                squares.add(new Square(color, x+unitSize, y+unitSize, unitSize, 0.5f));
                break;
            case R:
                squares.add(new Square(color, x, y, unitSize, 0.5f));
                squares.add(new Square(color, x+unitSize, y, unitSize, 0.5f));
                squares.add(new Square(color, x, y+unitSize, unitSize, .5f));
                squares.add(new Square(color, x+unitSize, y+unitSize, unitSize, 0.5f));
                break;
            case S:
                squares.add(new Square(color, x, y, unitSize, 0.5f));
                squares.add(new Square(color, x+unitSize, y, unitSize, 0.5f));
                squares.add(new Square(color, x+unitSize, y+unitSize, unitSize, 0.5f));
                squares.add(new Square(color, x+(2*unitSize), y+unitSize, unitSize, 0.5f));
                break;
        }
        return squares;
    }
}
