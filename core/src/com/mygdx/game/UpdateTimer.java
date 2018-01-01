package com.mygdx.game;

import com.badlogic.gdx.Gdx;

/**
 * Created by aditisri on 12/21/17.
 */
public class UpdateTimer implements Runnable{
    private long startTime;
    private Tetromino fallingPiece;
    private boolean isRunning;
    public UpdateTimer(Tetromino t){
       startTime = System.currentTimeMillis();
       fallingPiece = t;
       isRunning = true;
    }


    @Override
    public void run() {
        int count = 0;
        while(fallingPiece.isFalling()){
            if((System.currentTimeMillis()-startTime) > 0 && (System.currentTimeMillis()-startTime)%Tetromino.timestep == 0) {
                fallingPiece.setShouldMoveDown(true);
//                fallingPiece.move(Move.DOWN);
                this.stop();
                Gdx.graphics.requestRendering();
                count++;
                break;
            }
        }
    }

    public boolean isRunning(){
        return isRunning;
    }

    public void stop(){
        this.isRunning = false;
    }

    public void resume(){
        this.startTime = System.currentTimeMillis();
        this.isRunning = true;
    }

}
