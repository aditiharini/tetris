package com.mygdx.game;

import com.google.gson.Gson;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by aditisri on 1/7/18.
 */
public class CommunicationManager {
//    private static InetAddress serverIp = InetAddress.getLocalHost();
//    private static int serverPort = 8000;
//    private static Socket socket = initiateSocket();
//
//    public static Socket initiateSocket(){
//        try {
//            return new Socket(InetAddress.getLocalHost(), 5000, serverIp, serverPort);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    public static Message generateMessage(Message.Type type){
        switch (type){
            case NewGameRequest:
                return new NewGameRequest();
            case NewGameResponse:
                return null;
        }
        return null;
    }

    public static void sendMessage(Message.Type type, InetAddress sourceIp, int sourcePort, InetAddress destIp, int destPort){
        Socket s = null;
        try {
            s = new Socket(destIp, destPort);
            Writer out = new OutputStreamWriter(s.getOutputStream());
            Reader in = new InputStreamReader(s.getInputStream());
            Message m = generateMessage(type);
            m.writeJsonToStream(out);
            m.handleResponse(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Message receiveMessage(Reader in) {
        Gson g = new Gson();
        try {
            return (Message) g.fromJson(in, Class.forName("Message"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
