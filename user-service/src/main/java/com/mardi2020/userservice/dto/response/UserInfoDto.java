package com.mardi2020.userservice.dto.response;

import com.mardi2020.userservice.repository.UserEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserInfoDto {

    private Long userId;

    private String email;

    private String name;

    public UserInfoDto(UserEntity user) {
        this.userId = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
    }
}
