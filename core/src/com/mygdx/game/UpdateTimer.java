package com.mygdx.game;

import com.badlogic.gdx.Gdx;

/**
 * Created by aditisri on 12/21/17.
 */
public class UpdateTimer implements Runnable{
    private long startTime;
    private Tetromino fallingPiece;
    public UpdateTimer(Tetromino t){
       startTime = System.currentTimeMillis();
       fallingPiece = t;
    }


    @Override
    public void run() {
        while(fallingPiece.isFalling()){
            if((System.currentTimeMillis()-startTime)%Tetromino.timestep == 0) {
                fallingPiece.setShouldMoveDown(true);
//                fallingPiece.move(Move.DOWN);
                Gdx.graphics.requestRendering();
            }
        }
    }
}
