package com.mardi2020.joinservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JoinResultDto {

    private boolean success;

    private String message;

    public static JoinResultDto setting(boolean success, String message) {
        return JoinResultDto.builder()
                .success(success)
                .message(message)
                .build();
    }
}

