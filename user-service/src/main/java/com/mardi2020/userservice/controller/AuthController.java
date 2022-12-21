package com.mardi2020.userservice.controller;

import com.mardi2020.userservice.dto.response.JwtTokenDto;
import com.mardi2020.userservice.dto.response.RefreshTokenResponse;
import com.mardi2020.userservice.service.AccessTokenService;
import com.mardi2020.userservice.service.RefreshTokenService;
import com.mardi2020.userservice.util.CookieUtils;
import javax.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/access-token")
public class AuthController {

    private final AccessTokenService accessTokenService;

    private final RefreshTokenService refreshTokenService;

    private final CookieUtils cookieUtils;

    @GetMapping("/reissue")
    public ResponseEntity<?> refreshToken(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String accessToken,
                                          @CookieValue(name = "refresh-token", required = false) String refreshToken) {
        if (refreshToken == null) {
            return ResponseEntity.badRequest()
                    .body("Refresh token is not valid. try to login again.");
        }

        JwtTokenDto jwtTokenDto = refreshTokenService.refreshJwtToken(accessToken, refreshToken);
        ResponseCookie refreshTokenCookie = cookieUtils.createRefreshTokenCookie(refreshToken);

        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .body(new RefreshTokenResponse(jwtTokenDto));
    }

    @GetMapping("/check")
    public ResponseEntity<?> checkAccessToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        accessTokenService.isValidAccessToken(authorization);

        return ResponseEntity.status(HttpStatus.OK)
                .body("check access token");
    }
}
