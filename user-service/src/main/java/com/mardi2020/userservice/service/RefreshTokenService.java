package com.mardi2020.userservice.service;

import com.mardi2020.userservice.dto.response.JwtTokenDto;
import com.mardi2020.userservice.exception.AccessTokenNotValidException;
import com.mardi2020.userservice.exception.RefreshTokenNotValidException;
import com.mardi2020.userservice.exception.UserNotFoundException;
import com.mardi2020.userservice.repository.TokenEntity;
import com.mardi2020.userservice.repository.TokenRedisRepository;
import com.mardi2020.userservice.repository.UserEntity;
import com.mardi2020.userservice.repository.UserRepository;
import com.mardi2020.userservice.util.JwtUtils;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RefreshTokenService {

    private final UserDetailsService userService;

    private final JwtUtils jwtUtils;

    private final UserRepository userRepository;

    private final TokenRedisRepository tokenRepository;

    @Transactional
    public void updateRefreshToken(Long id, String tokenId) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("user not found");
        }
        tokenRepository.save(TokenEntity.of(id.toString(), tokenId));
    }

    @Transactional
    public JwtTokenDto refreshJwtToken(String accessToken, String refreshToken) {
        String userId = jwtUtils.getUserId(accessToken.replace("Bearer", ""));

        TokenEntity targetToken = tokenRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("저장된 정보가 존재하지 않습니다.")
        );

        String targetTokenId = targetToken.getTokenId();
        String findTokenId = jwtUtils.getRefreshTokenId(refreshToken.replace("Bearer", ""));

        if (!jwtUtils.isValidToken(refreshToken.replace("Bearer", "")) || !targetTokenId.equals(findTokenId)) {
            tokenRepository.delete(targetToken);
            throw new RefreshTokenNotValidException();
        }

        UserEntity user = userRepository.findById(Long.valueOf(userId)).orElseThrow(
                () -> new UserNotFoundException("user not found"));

        String newAccessToken = jwtUtils.generateAccessToken(user.getId(), user.getRoles());
        Date expiredTime = jwtUtils.getExpiredTime(newAccessToken);

        return JwtTokenDto.builder()
                .accessToken(newAccessToken)
                .refreshToken(refreshToken)
                .accessTokenExpiredDate(expiredTime)
                .build();
    }

    public void tokenByLogout(String accessToken) {
        if (!jwtUtils.isValidToken(accessToken)) {
            throw new AccessTokenNotValidException();
        }

        String userId = jwtUtils.getUserId(accessToken);
        TokenEntity refreshToken = tokenRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("저장된 정보가 존재하지 않습니다.")
        );

        tokenRepository.delete(refreshToken);
    }
}
