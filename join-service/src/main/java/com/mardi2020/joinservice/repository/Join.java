package com.mardi2020.joinservice.repository;

import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "join")
@NoArgsConstructor
public class Join {

    @Id
    private String id;
    private Type type;

    @Builder
    public Join(String id, String type) {
        this.id = id;
        this.type = Type.valueOf(type);
    }

    public enum Type {
        EMAIL, NAME
    }
}
