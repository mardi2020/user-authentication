package com.mardi2020.joinservice.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PasswordNotValidException extends RuntimeException {

    public PasswordNotValidException(String message) {
        super(message);
    }

    public PasswordNotValidException(String message, Throwable cause) {
        super(message, cause);
    }
}
