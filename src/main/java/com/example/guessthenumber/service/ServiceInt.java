package com.example.guessthenumber.service;

import com.example.guessthenumber.dto.Game;
import com.example.guessthenumber.dto.Round;
import com.example.guessthenumber.exception.InvalidGuessNumberException;

import java.util.List;

public interface ServiceInt {

    Game createGame();
    Round makeGuess(String guess,int gameid) throws InvalidGuessNumberException;
    List<Game> getFinishedGames();
    Game getGameById(int id);
    List<Round> getRoundsForGame(int id);
}
