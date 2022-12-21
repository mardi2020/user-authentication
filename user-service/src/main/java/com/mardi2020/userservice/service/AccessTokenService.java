package com.mardi2020.userservice.service;

import com.mardi2020.userservice.exception.AccessTokenNotValidException;
import com.mardi2020.userservice.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccessTokenService {

    private final JwtUtils jwtUtils;

    public void isValidAccessToken(String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer", "");

        if (!jwtUtils.isValidToken(token)) {
            throw new AccessTokenNotValidException();
        }
    }
}
