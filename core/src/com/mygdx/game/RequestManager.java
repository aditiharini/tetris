package com.mygdx.game;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.Socket;

/**
 * Created by aditisri on 1/6/18.
 */
public class RequestManager {
    public static void delegateRequest(Message m){
        switch(m.getType()){
            case NewGameRequest:
                break;

        }

    }
}
