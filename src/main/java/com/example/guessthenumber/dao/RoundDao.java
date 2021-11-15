package com.example.guessthenumber.dao;

import com.example.guessthenumber.dto.Game;
import com.example.guessthenumber.dto.Round;

import java.util.List;

public interface RoundDao {
   Round addRound(Round round);
   List<Round> getRoundsByGame(int roundId);
   void removeRound(int roundId);
   Round getRoundById(int roundId);
}
