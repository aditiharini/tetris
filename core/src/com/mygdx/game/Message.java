package com.mygdx.game;

import com.google.gson.Gson;

import java.io.Reader;
import java.io.Writer;

/**
 * Created by aditisri on 1/8/18.
 */
public abstract class Message {
    public enum Type{
        NewGameRequest, NewGameResponse
    }

    public abstract String getHeader();

    public abstract Type getType();

    public abstract void handleResponse(Reader in);


    public void writeJsonToStream(Writer out){
        Gson g = new Gson();
        g.toJson(this, this.getClass(), out);
    }
}
