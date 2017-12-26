package com.mygdx.game;

/**
 * Created by aditisri on 12/24/17.
 */
public enum Orientation {
    UP, LEFT, DOWN, RIGHT;

    public Orientation getNextCounterClockwise(){
        switch(this){
            case UP:
                return LEFT;
            case LEFT:
                return DOWN;
            case DOWN:
                return RIGHT;
            case RIGHT:
                return UP;
        }
        return null;
    }

    public Orientation getNextClockwise(){
        switch(this){
            case UP:
                return RIGHT;
            case LEFT:
                return UP;
            case DOWN:
                return LEFT;
            case RIGHT:
                return DOWN;
        }
        return null;
    }
}
