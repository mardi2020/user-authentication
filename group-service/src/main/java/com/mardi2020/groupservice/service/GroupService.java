package com.mardi2020.groupservice.service;

import com.mardi2020.groupservice.dto.CreateGroupDto;
import com.mardi2020.groupservice.dto.GroupInfoDto;
import com.mardi2020.groupservice.dto.JoinGroupDto;
import com.mardi2020.groupservice.dto.LeaveGroupDto;
import com.mardi2020.groupservice.exception.GroupNotFoundException;
import com.mardi2020.groupservice.repository.GroupEntity;
import com.mardi2020.groupservice.repository.GroupRepository;
import com.mardi2020.groupservice.repository.GroupUsers;
import com.mardi2020.groupservice.repository.GroupUsersRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GroupService {

    private final GroupRepository groupRepository;

    private final GroupUsersRepository groupUsersRepository;

    @Transactional
    public void createGroup(CreateGroupDto groupDto) {
        GroupEntity groupEntity = GroupEntity.builder()
                .name(groupDto.getGroupName())
                .build();

        GroupEntity savedGroup = groupRepository.save(groupEntity);
        log.info("[groups PK] : " + savedGroup.getId());
        GroupUsers user = GroupUsers.builder()
                .groupEntity(savedGroup)
                .userId(groupDto.getUserId())
                .build();

        groupUsersRepository.save(user);
    }

    @Transactional
    public void joinGroup(JoinGroupDto joinGroupDto) {
        Long groupId = joinGroupDto.getGroupId();
        log.info("[groupId] :" + groupId);
        GroupEntity groupEntity = groupRepository.findById(groupId).orElseThrow(
                () -> new GroupNotFoundException("group not found")
        );
        GroupUsers user = GroupUsers.builder()
                .userId(joinGroupDto.getUserId())
                .groupEntity(groupEntity)
                .build();

        groupUsersRepository.save(user);
    }

    @Transactional
    public void leaveGroup(LeaveGroupDto leaveGroupDto) {
        Long groupId = leaveGroupDto.getGroupId();
        GroupEntity groupEntity = groupRepository.findById(groupId).orElseThrow(
                () -> new GroupNotFoundException("group이 존재하지 않습니다.")
        );
        GroupUsers groupUser = groupUsersRepository.findByGroupEntityAndUserId(groupEntity, groupId);
        groupUsersRepository.delete(groupUser);

        if (groupUsersRepository.countByGroupEntity(groupEntity) == 0) {
            groupRepository.delete(groupEntity);
        }
    }

    public GroupInfoDto getGroupInfo(Long groupId) {
        List<GroupUsers> groupUsers = groupUsersRepository.findAllByGroupId(groupId);
        GroupEntity group = groupRepository.findById(groupId).orElseThrow(
                () -> new GroupNotFoundException("group not found")
        );

        return GroupInfoDto.builder()
                .groupName(group.getName())
                .groupId(groupId)
                .users(groupUsers.stream().map(
                        GroupUsers::getUserId
                ).collect(Collectors.toList()))
                .build();
    }
}
