package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;

/**
 * Created by aditisri on 12/21/17.
 */
public class UpdateTimer implements Runnable{
    private long startTime;
    private TetrisGame game;
    private boolean isRunning;
    public UpdateTimer(TetrisGame game){
       startTime = System.currentTimeMillis();
       isRunning = true;
       this.game = game;
    }


    @Override
    public void run() {
        while(true){
            if(!game.getIsGameActive()) break;
            if(this.isRunning && (System.currentTimeMillis()-startTime) > 0 && (System.currentTimeMillis()-startTime)%game.timestep == 0) {
                this.stop();
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        game.moveFallingPieceDown();
                    }
                });
                this.resume();
            }
        }
    }

    public void stop(){
        this.isRunning = false;
    }

    public void resume(){
        this.startTime = System.currentTimeMillis();
        this.isRunning = true;
    }

}
