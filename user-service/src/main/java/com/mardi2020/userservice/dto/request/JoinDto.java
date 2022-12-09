package com.mardi2020.userservice.dto.request;

import com.mardi2020.userservice.repository.UserEntity;
import javax.validation.constraints.Email;
import lombok.Data;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class JoinDto {

    @Email
    private String email;

    @Setter
    private String password;

    private String name;

    public static UserEntity toEntity(JoinDto joinDto, PasswordEncoder passwordEncoder) {
        return UserEntity.builder()
                .email(joinDto.getEmail())
                .password(passwordEncoder.encode(joinDto.getPassword()))
                .name(joinDto.getName())
                .build();
    }
}
