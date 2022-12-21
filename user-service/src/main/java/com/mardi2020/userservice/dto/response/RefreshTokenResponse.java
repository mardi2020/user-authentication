package com.mardi2020.userservice.dto.response;

import java.text.SimpleDateFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshTokenResponse {

    private String accessToken;

    private String expiredTime;

    public RefreshTokenResponse(JwtTokenDto jwtTokenDto) {
        accessToken = jwtTokenDto.getAccessToken();
        expiredTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(jwtTokenDto.getAccessTokenExpiredDate());
    }
}
