package com.mardi2020.findinfoservice.dto.request;

import lombok.Data;

@Data
public class ChangePwDto {

    private String email;

    private String password;

    private String name;
}
