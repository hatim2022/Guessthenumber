package com.example.guessthenumber.dao;

import com.example.guessthenumber.dto.Game;
import com.example.guessthenumber.dto.Status;

import java.util.List;

public interface GameDao {
 Game createGame(Game game);
 List<Game> getAllGames();
 Game getGameById(int gameId);
 void updateGameStatus(int gameId, Status status);
 void deleteGameById(int gameId);
}
