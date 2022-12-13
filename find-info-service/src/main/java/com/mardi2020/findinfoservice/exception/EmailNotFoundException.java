package com.mardi2020.findinfoservice.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EmailNotFoundException extends RuntimeException {

    public EmailNotFoundException(String msg) {
        super(msg);
    }
}
