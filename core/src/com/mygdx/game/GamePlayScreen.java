package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import sun.jvm.hotspot.asm.sparc.SPARCRegister;

/**
 * Created by aditisri on 1/5/18.
 */
public class GamePlayScreen implements Screen {
    public TetrisGame parent;
    public SpriteBatch batch;
    public GamePlayScreen(TetrisGame g){
        parent = g;
        Gdx.input.setInputProcessor(new UIHandler(parent));
        batch = new SpriteBatch();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        System.out.println("render board");
        Gdx.gl.glClearColor(0, 0, 0 ,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
//        parent.getGrid().drawBackground();
        parent.getFallingPiece().draw(batch);
        parent.getGrid().draw(batch);
        batch.end();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
