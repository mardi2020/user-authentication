package com.mardi2020.userservice.repository;

import org.springframework.data.repository.CrudRepository;

public interface TokenRedisRepository extends CrudRepository<TokenEntity, String> {
}
