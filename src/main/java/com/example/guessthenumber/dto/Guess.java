package com.example.guessthenumber.dto;

public class Guess {
    private String guessNumber;
    private int gameId;


    public String getGuessNumber() {
        return guessNumber;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGuessNumber(String guessNumber) {
        this.guessNumber = guessNumber;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }
}
