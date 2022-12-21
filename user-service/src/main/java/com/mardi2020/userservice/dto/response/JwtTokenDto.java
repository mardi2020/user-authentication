package com.mardi2020.userservice.dto.response;


import java.util.Date;
import lombok.Builder;
import lombok.Data;

@Data
public class JwtTokenDto {

    private String accessToken;

    private String refreshToken;

    private Date accessTokenExpiredDate;

    @Builder
    public JwtTokenDto(String accessToken, String refreshToken, Date accessTokenExpiredDate) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.accessTokenExpiredDate = accessTokenExpiredDate;
    }
}
