package com.mardi2020.groupservice.controller;

import com.mardi2020.groupservice.dto.CreateGroupDto;
import com.mardi2020.groupservice.dto.GroupInfoDto;
import com.mardi2020.groupservice.dto.JoinGroupDto;
import com.mardi2020.groupservice.dto.LeaveGroupDto;
import com.mardi2020.groupservice.service.GroupService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/group")
@RequiredArgsConstructor
@Slf4j
public class GroupController {

    private final GroupService groupService;

    @ApiOperation(value = "group 생성하기")
    @PostMapping
    public ResponseEntity<?> createGroup(@RequestBody CreateGroupDto createGroupDto) {
        groupService.createGroup(createGroupDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("create group success");
    }

    @ApiOperation(value = "이미 만들어진 group에 사용자 초대")
    @PostMapping("/{groupId}/{userId}")
    public ResponseEntity<?> joinGroup(@PathVariable Long groupId, @PathVariable Long userId) {
        groupService.joinGroup(new JoinGroupDto(userId, groupId));
        return ResponseEntity.status(HttpStatus.OK).body("join success");
    }

    @ApiOperation(value = "가입된 group 정보 보기")
    @GetMapping("/{userId}")
    public ResponseEntity<?> getMyGroup(@PathVariable Long userId) {
        GroupInfoDto groupInfo = groupService.getGroupInfo(userId);
        return ResponseEntity.status(HttpStatus.OK).body(groupInfo);
    }

    @ApiOperation(value = "가입되어있는 group 탈퇴하기")
    @DeleteMapping("/{groupId}/{userId}")
    public ResponseEntity<?> leaveGroup(@PathVariable Long groupId, @PathVariable Long userId) {
        groupService.leaveGroup(new LeaveGroupDto(groupId, userId));
        return ResponseEntity.status(HttpStatus.OK).body("delete success");
    }

}
