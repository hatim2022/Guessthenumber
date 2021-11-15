package com.example.guessthenumber.controller;

import com.example.guessthenumber.dto.Game;
import com.example.guessthenumber.dto.Guess;
import com.example.guessthenumber.dto.Round;
import com.example.guessthenumber.exception.InvalidGuessNumberException;
import com.example.guessthenumber.service.ServiceInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api")
public class Controller {

    @Autowired
    ServiceInt service;

    @GetMapping
    public String helloWorld() {
        return "hello world";
    }


    @PostMapping("/begin")
    @ResponseStatus(HttpStatus.CREATED)
    public Game begin() {
        return service.createGame();
    }


    @PostMapping("/guess")
    public Round guess(@RequestBody Guess guess) throws InvalidGuessNumberException {
       Round round;
        try {
            round=service.makeGuess(guess.getGuessNumber(),guess.getGameId());
        }catch (InvalidGuessNumberException exp) {
               throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "invalid Guess number", exp);
        }
        return round;
    }

    @GetMapping("/game")
    public List<Game> getGames(){
        return service.getFinishedGames();
    }

    @GetMapping("/game/{gameId}")
    public Game getGameById(@PathVariable int gameId){
       return service.getGameById(gameId);
    }

    @GetMapping("/rounds/{gameId}")
    public List<Round> getGameRounds(@PathVariable int gameId){
      return service.getRoundsForGame(gameId);
    }
}
