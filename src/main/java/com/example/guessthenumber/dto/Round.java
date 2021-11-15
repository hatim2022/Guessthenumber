package com.example.guessthenumber.dto;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

public class Round {
    private int id;
    private String guess;
    private Date time;
    private int exactMatch;
    private int partialMatch;
    private int gameID;

    public Round() {
    }

    public Round( String guess, Date time, int gameID) {
        this.guess = guess;
        this.time = time;
        this.gameID = gameID;
        this.exactMatch=0;
        this.partialMatch=0;
    }

    public String getGuess() {
        return guess;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getExactMatch() {
        return exactMatch;
    }

    public int getPartialMatch() {
        return partialMatch;
    }


    public void setGuess(String guess) {
        this.guess = guess;
    }



    public void setExactMatch(int exactMatch) {
        this.exactMatch = exactMatch;
    }

    public void setPartialMatch(int partialMatch) {
        this.partialMatch = partialMatch;
    }


    public String getResult(){
        return "e:"+exactMatch+":p:"+partialMatch;
    }

    public void setMatch(String result){
        String[] res=result.split(":");
        setExactMatch(Integer.parseInt(res[1]));
        setPartialMatch(Integer.parseInt(res[3]));
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Round round = (Round) o;
        return id == round.id && exactMatch == round.exactMatch && partialMatch == round.partialMatch && gameID == round.gameID && Objects.equals(guess, round.guess)
                && Objects.equals(formatter.format(time), formatter.format(round.time));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, guess, time, exactMatch, partialMatch, gameID);
    }
}

