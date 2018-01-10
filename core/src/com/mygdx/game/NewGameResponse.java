package com.mygdx.game;

import java.io.Reader;

/**
 * Created by aditisri on 1/9/18.
 */
public class NewGameResponse extends Message{
    private static final String header = "New Game Response";
    private static final Message.Type type = Type.NewGameResponse;
    private GameManager.NewGameStatus status;
    public NewGameResponse(GameManager.NewGameStatus status){
        this.status = status;
    }

    @Override
    public String getHeader() {
        return header;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public void handleResponse(Reader in) {

    }
}
