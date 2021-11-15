package com.example.guessthenumber.exception;

public class InvalidGuessNumberException extends Exception {

    public InvalidGuessNumberException() {
    }

    public InvalidGuessNumberException(String message) {
        super(message);
    }

}
