package com.mardi2020.userservice.exception;

public class AccessTokenNotValidException extends RuntimeException {

    private static final String MESSAGE = "ACCESS TOKEN NOT VALID";

    public AccessTokenNotValidException() {
        super(MESSAGE);
    }
}
