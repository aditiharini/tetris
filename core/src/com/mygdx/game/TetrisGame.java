package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

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
	public Skin skin;
	public Stage stage;
	public boolean newGameScreen;

	@Override
	public void create () {
		newGameScreen = true;
		batch = new SpriteBatch();
		fallingPiece = null;
		landedPieces = new ArrayList<Tetromino>();
		Gdx.graphics.setContinuousRendering(false);
		Gdx.graphics.requestRendering();
		currentTime = System.currentTimeMillis();
		turnLock = new ReentrantLock();
		grid = new Grid(40*0.5f);
		skin = new Skin();
		skin.add("default", new BitmapFont());
		Pixmap pixmap = new Pixmap((int)Gdx.graphics.getWidth()/4,(int)Gdx.graphics.getHeight()/10, Pixmap.Format.RGB888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		skin.add("background",new Texture(pixmap));
		TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
		textButtonStyle.up = skin.newDrawable("background", Color.GRAY);
		textButtonStyle.down = skin.newDrawable("background", Color.DARK_GRAY);
		textButtonStyle.checked = skin.newDrawable("background", Color.DARK_GRAY);
		textButtonStyle.over = skin.newDrawable("background", Color.LIGHT_GRAY);
		textButtonStyle.font = skin.getFont("default");
		skin.add("default", textButtonStyle);
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		TextButton newGameButton = new TextButton("New game", skin); // Use the initialized skin
		newGameButton.setPosition(Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/8 , Gdx.graphics.getHeight()/2);
		stage.addActor(newGameButton);
		newGameButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent e, float x, float y){
			    newGameScreen = false;

			}
		});

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0 ,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if(newGameScreen) {
			stage.act();
			stage.draw();
		}
		else {

			currentTime = System.currentTimeMillis();
			if (fallingPiece == null) {
				fallingPiece = new Tetromino(Gdx.graphics.getWidth() / 2 - ((Gdx.graphics.getWidth() / 2)%20), Gdx.graphics.getHeight(), Color.CYAN);
				Gdx.input.setInputProcessor(new UIHandler(fallingPiece));
			}

			grid.drawBackground();


			turnLock.lock();

			batch.begin();
			fallingPiece.move(Move.DOWN);
			grid.draw(batch);
			fallingPiece.draw(batch);
//		for(Tetromino t : landedPieces){
//			t.draw(batch);
//		}
			this.grid.handleDeletions();

			if(this.fallingPiece.isGameOver()){
				newGameScreen = true;
				grid = new Grid(40*0.5f);
				landedPieces = new ArrayList<Tetromino>();
				fallingPiece = null;
				turnLock = new ReentrantLock();
				Gdx.input.setInputProcessor(stage);
				batch.end();
				return;
			}

			if (!fallingPiece.isFalling()) {
				landedPieces.add(fallingPiece);
				grid.addPiece(fallingPiece);
				fallingPiece = null;
				Gdx.graphics.requestRendering();
			}


			batch.end();
			turnLock.unlock();
		}

	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
