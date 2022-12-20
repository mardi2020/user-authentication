package com.mardi2020.findinfoservice.dto.response;

import java.io.Serializable;
import lombok.Data;

@Data
public class UserDto implements Serializable {

    private Long userId;

    private String email;

    private String name;

    private String role;

}
