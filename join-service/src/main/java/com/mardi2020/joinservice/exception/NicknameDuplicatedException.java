package com.mardi2020.joinservice.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NicknameDuplicatedException extends RuntimeException {

    public NicknameDuplicatedException(String message) {
        super(message);
    }

    public NicknameDuplicatedException(String message, Throwable cause) {
        super(message, cause);
    }
}
