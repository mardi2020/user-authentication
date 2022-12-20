package com.mardi2020.groupservice.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class GroupNotFoundException extends RuntimeException {

    public GroupNotFoundException(String msg) {
        super(msg);
    }
}
