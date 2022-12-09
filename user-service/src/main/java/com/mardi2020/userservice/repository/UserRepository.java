package com.mardi2020.userservice.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    UserEntity findByEmail(String username);

    boolean existsByEmail(String email);

    boolean existsByName(String nickname);

    List<UserEntity> findAll();
}
