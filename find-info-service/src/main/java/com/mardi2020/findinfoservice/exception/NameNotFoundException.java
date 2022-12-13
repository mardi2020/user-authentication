package com.mardi2020.findinfoservice.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NameNotFoundException extends RuntimeException {

    public NameNotFoundException(String msg) {
        super(msg);
    }
}
