package com.mygdx.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;

/**
 * Created by aditisri on 12/21/17.
 */
public class UIHandler extends InputAdapter{
    private Tetromino fallingPiece;
    public UIHandler(Tetromino t){
        fallingPiece = t;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch(keycode){
            case Input.Keys.H:
                fallingPiece.rotateCounterClockwise();
                break;
            case Input.Keys.SEMICOLON:
                fallingPiece.rotateClockwise();
                break;
            case Input.Keys.L:
                fallingPiece.updateRight();
                break;
            case Input.Keys.J:
                fallingPiece.updateLeft();
                break;
            case Input.Keys.K:
                fallingPiece.setShouldMoveDown(true);
                fallingPiece.updateDown();

        }
        return true;
    }

}
