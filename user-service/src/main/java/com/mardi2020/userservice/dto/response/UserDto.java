package com.mardi2020.userservice.dto.response;

import com.mardi2020.userservice.repository.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long userId;

    private String email;

    private String name;

    public UserDto(UserEntity user) {
        this.userId = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
    }
}
