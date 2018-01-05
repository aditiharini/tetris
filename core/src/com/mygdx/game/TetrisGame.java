package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TetrisGame extends Game{
	SpriteBatch batch;
	private Tetromino fallingPiece;
	private Lock turnLock;
	public static Grid grid;
	public UpdateTimer timer;
	public static float timestep = 1000;
	private boolean isGameActive;

	@Override
	public void create () {
		isGameActive = false;
		batch = new SpriteBatch();
		Gdx.graphics.setContinuousRendering(false);
		Gdx.graphics.requestRendering();
		turnLock = new ReentrantLock();
		fallingPiece = new Tetromino(this, Gdx.graphics.getWidth() / 2 - ((Gdx.graphics.getWidth() / 2)%20), Gdx.graphics.getHeight());
		grid = new Grid(40*0.5f);
		timer = new UpdateTimer(this);
		this.setScreen(new NewGameScreen(this));
	}

	public boolean getIsGameActive(){
		return this.isGameActive;
	}

	public void setIsGameActive(boolean isActive){
		isGameActive = isActive;
	}

	public void startTimer(){
		Thread t = new Thread(timer);
		t.start();
	}

	public void resetGame(){
	    grid = new Grid(40*0.5f);
        fallingPiece = new Tetromino(this, Gdx.graphics.getWidth() / 2 - ((Gdx.graphics.getWidth() / 2)%20), Gdx.graphics.getHeight());

    }

    public Grid getGrid(){
		return this.grid;
	}


	public void moveFallingPieceDown(){
    	this.fallingPiece.move(Move.DOWN);
	}

	public void setNewFallingPiece(){
		fallingPiece = new Tetromino(this, Gdx.graphics.getWidth() / 2 - ((Gdx.graphics.getWidth() / 2)%20), Gdx.graphics.getHeight());
	}

	public Tetromino getFallingPiece(){
		return this.fallingPiece;
	}

	public void handleGameOver(){
		this.setScreen(new NewGameScreen(this));
		resetGame();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0 ,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		this.getScreen().render(Gdx.graphics.getDeltaTime());
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
