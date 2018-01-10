package com.mygdx.game;

import com.google.gson.Gson;

import java.io.Reader;
import java.io.Writer;

/**
 * Created by aditisri on 1/8/18.
 */
public class NewGameRequest extends Message{
    private static final String header = "New Game Request";
    private static final Message.Type type = Type.NewGameRequest;
    private GameManager.NewGameStatus status;
   public NewGameRequest(){

    }

    @Override
    public String getHeader() {
        return this.header;
    }

    @Override
    public Type getType() {
        return this.type;
    }

    @Override
    public void handleResponse(Reader in) {
        Gson g = new Gson();
        try {
            NewGameResponse res = (NewGameResponse) g.fromJson(in, Class.forName("NewGameResponse"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void generateResponse(){

    }



}
