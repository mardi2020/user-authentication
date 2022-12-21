package com.mardi2020.userservice.exception;

public class RefreshTokenNotValidException extends RuntimeException {

    private final static String MESSAGE = "TOKEN NOT VALID";

    public RefreshTokenNotValidException() {
        super(MESSAGE);
    }
}
