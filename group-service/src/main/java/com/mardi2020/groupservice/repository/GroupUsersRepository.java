package com.mardi2020.groupservice.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupUsersRepository extends CrudRepository<GroupUsers, Long> {

    @Query(nativeQuery = true, value = "select * from `group-service`.group_users where group_id = :id")
    List<GroupUsers> findAllByGroupId(@Param(value = "id") Long groupId);

    GroupUsers findByGroupEntityAndUserId(GroupEntity groupId, Long userId);

    int countByGroupEntity(GroupEntity groupEntity);

    GroupUsers findByUserId(Long userId);
}
