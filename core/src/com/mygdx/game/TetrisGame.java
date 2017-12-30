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
	public static Grid grid;

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
		currentTime = System.currentTimeMillis();
        if(fallingPiece == null ){
            fallingPiece = new Tetromino(200, 450, Color.CYAN);
			Gdx.input.setInputProcessor(new UIHandler(fallingPiece));
        }
        grid.draw();


		turnLock.lock();

		batch.begin();
		fallingPiece.move(Move.DOWN);
		fallingPiece.draw(batch);

        if (!fallingPiece.isFalling()){
            grid.addPiece(fallingPiece);
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
