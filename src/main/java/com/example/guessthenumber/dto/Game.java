package com.example.guessthenumber.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Game {
    private int id;
    private String answer;
    private Enum status;
    private List<Round> rounds;

    public Game() {
    }

    public Game(String answer, Enum status) {
        this.answer = answer;
        this.status = status;
        rounds=new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public List<Round> getRounds() {
        return rounds;
    }

    public void addRound(Round r){
        this.rounds.add(r);
    }

    public void setRounds(List<Round> rounds) {
        this.rounds = rounds;
    }

    public Enum getStatus() {
        return status;
    }

    public void setStatus(Enum status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return id == game.id && Objects.equals(answer, game.answer) && Objects.equals(status, game.status) && Objects.equals(rounds, game.rounds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, answer, status, rounds);
    }
}
