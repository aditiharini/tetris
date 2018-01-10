package com.mygdx.game;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.Reader;
import java.net.*;

/**
 * Created by aditisri on 1/6/18.
 */
public class GameServer extends Thread {
    private String ip;
    private int port;
    private ServerSocket socket;

    public GameServer(int port){
        this.port = port;
        try {
            this.socket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run(){
        while(true) {
            Socket s = null;
            try {
                s = this.socket.accept();
                Reader in = new InputStreamReader(s.getInputStream());
                Message m = CommunicationManager.receiveMessage(in);
                RequestManager.delegateRequest(m);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args){
        GameServer server = new GameServer(8000);
        server.start();

    }


}
