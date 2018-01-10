package com.mygdx.game;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by aditisri on 1/9/18.
 */
public class GameManager {
    private static List<TetrisGame> activeGames = new ArrayList<TetrisGame>();
    private static Queue<NewGameRequest> newGameRequests = new ArrayDeque<NewGameRequest>();
    public enum NewGameStatus{
        WAITING, CREATED, DENIED
    }

    public synchronized NewGameResponse handleNewGameRequest(NewGameRequest req){
        newGameRequests.add(req);
        if(newGameRequests.size() < 2){
            return new NewGameResponse(NewGameStatus.WAITING);

        }
        else{

        }
    }

    public void endGame(int gameId){

    }





}
