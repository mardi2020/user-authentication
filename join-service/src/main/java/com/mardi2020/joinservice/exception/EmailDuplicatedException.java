package com.mardi2020.joinservice.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EmailDuplicatedException extends RuntimeException {

    public EmailDuplicatedException(String message) {
        super(message);
    }

    public EmailDuplicatedException(String message, Throwable cause) {
        super(message, cause);
    }
}
