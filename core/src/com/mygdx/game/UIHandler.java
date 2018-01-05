package com.mygdx.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;

/**
 * Created by aditisri on 12/21/17.
 */
public class UIHandler extends InputAdapter{
    private TetrisGame game;
    public UIHandler(TetrisGame t){
        game = t;
    }

    @Override
    public boolean keyDown(int keycode) {
        Tetromino t = game.getFallingPiece();
        switch(keycode){
            case Input.Keys.H:
                t.move(Move.TURN_LEFT);
                break;
            case Input.Keys.SEMICOLON:
                t.move(Move.TURN_RIGHT);
                break;
            case Input.Keys.L:
                System.out.println("right key down");
                t.move(Move.RIGHT);
                break;
            case Input.Keys.J:
                t.move(Move.LEFT);
                break;
            case Input.Keys.K:
                System.out.println("down key down");
                t.move(Move.DOWN);
//                fallingPiece.updateDown();

        }
        return true;
    }

}
