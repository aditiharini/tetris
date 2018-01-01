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
                fallingPiece.move(Move.TURN_LEFT);
                break;
            case Input.Keys.SEMICOLON:
                fallingPiece.move(Move.TURN_RIGHT);
                break;
            case Input.Keys.L:
                System.out.println("right key down");
                fallingPiece.move(Move.RIGHT);
                break;
            case Input.Keys.J:
                fallingPiece.move(Move.LEFT);
                break;
            case Input.Keys.K:
                System.out.println("down key down");
                fallingPiece.setShouldMoveDown(true);
                fallingPiece.move(Move.DOWN);
//                fallingPiece.updateDown();

        }
        return true;
    }

}
