package com.mardi2020.userservice.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;


@Getter
@RedisHash("refresh_token")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenEntity {

    @Id
    private String userId;

    private String tokenId;


    public static TokenEntity of(String userId, String tokenId) {
        return TokenEntity.builder()
                .userId(userId)
                .tokenId(tokenId)
                .build();
    }
}
