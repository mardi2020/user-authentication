package com.mardi2020.joinservice.dto.request;

import java.io.Serializable;
import javax.validation.constraints.Email;
import lombok.Data;

@Data
public class JoinDto implements Serializable {

    @Email
    private String email;

    private String password;

    private String name;

}

