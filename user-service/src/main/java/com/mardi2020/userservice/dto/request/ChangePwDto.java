package com.mardi2020.userservice.dto.request;

import lombok.Data;

@Data
public class ChangePwDto {

    private String email;

    private String password;

    private String name;
}
