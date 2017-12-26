package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TetrisGame extends ApplicationAdapter {
	SpriteBatch batch;
	Tetromino fallingPiece;
	static int NUM_UPDATES_PER_FRAME = 3;
	private long currentTime;
	public static List<Tetromino> landedPieces;
	private Lock turnLock;
	private Grid grid;

	@Override
	public void create () {
		batch = new SpriteBatch();
		fallingPiece = null;
		landedPieces = new ArrayList<Tetromino>();
		Gdx.graphics.setContinuousRendering(false);
		Gdx.graphics.requestRendering();
		currentTime = System.currentTimeMillis();
		turnLock = new ReentrantLock();
		grid = new Grid(40*0.5f);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0 ,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//		long newTime = System.currentTimeMillis();
//		float frameTime = (newTime-currentTime)/1000f;
		currentTime = System.currentTimeMillis();
        if(fallingPiece == null ){
            fallingPiece = new Tetromino(200, 450, Color.CYAN);
			Gdx.input.setInputProcessor(new UIHandler(fallingPiece));
        }
        grid.draw();

//		int updateCount = 0;
//		while(frameTime > 0f && updateCount < NUM_UPDATES_PER_FRAME){
//		    float deltaTime = Math.min(frameTime, 1f/60f);

//
//		while(System.currentTimeMillis()-currentTime < 1000) {
//			if (updateCount<NUM_UPDATES_PER_FRAME) fallingPiece.updateDown();
//		    updateCount++;
//		}
//		    frameTime -= deltaTime;
//		    updateCount++;
//        }

//        for(Tetromino t: landedPieces){
//		    if(fallingPiece.collidesWith(t)){
//		        fallingPiece.handleCollision(t);
//            }
//        }
		turnLock.lock();

		batch.begin();
		fallingPiece.move(Move.DOWN);
		for(Tetromino t:landedPieces){
		    t.draw(batch);
        }
		fallingPiece.draw(batch);

        if (!fallingPiece.isFalling()){
            landedPieces.add(fallingPiece);
            fallingPiece = null;
        }

		batch.end();
        turnLock.unlock();

	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
