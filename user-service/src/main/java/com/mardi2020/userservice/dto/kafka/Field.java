package com.mardi2020.userservice.dto.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Field {

    private String type;

    private boolean optional;

    private String field;
}
