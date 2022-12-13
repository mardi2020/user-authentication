package com.mardi2020.findinfoservice.repository;

import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "")
@NoArgsConstructor
public class Info {

    @Id
    private String id;

    private String value;

}
