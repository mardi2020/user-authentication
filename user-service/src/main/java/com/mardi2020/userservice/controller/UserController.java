package com.mardi2020.userservice.controller;

import com.mardi2020.userservice.dto.request.ChangePwDto;
import com.mardi2020.userservice.dto.request.JoinDto;
import com.mardi2020.userservice.dto.request.UpdateNameDto;
import com.mardi2020.userservice.dto.response.FindResultDto;
import com.mardi2020.userservice.dto.response.JoinResultDto;
import com.mardi2020.userservice.dto.response.LeaveResultDto;
import com.mardi2020.userservice.dto.response.UserDto;
import com.mardi2020.userservice.dto.response.UserInfoDto;
import com.mardi2020.userservice.exception.UserNotFoundException;
import com.mardi2020.userservice.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<JoinResultDto> joinUser(@RequestBody JoinDto joinDto) {
        try {
            JoinResultDto result = userService.join(joinDto);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(JoinResultDto.setting(false, "SIGN UP FAILED"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<?> getUserInfo(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        try {
            UserDto user = userService.getMyInfo(token);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("token not valid", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping
    ResponseEntity<LeaveResultDto> leave(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        LeaveResultDto leaveResultDto = userService.deleteUser(token);
        return new ResponseEntity<>(leaveResultDto, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getUserAll() {
        try {
            List<UserInfoDto> userAll = userService.getUserAll();
            return new ResponseEntity<>(userAll, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/password")
    ResponseEntity<FindResultDto> changePassword(@RequestBody ChangePwDto changePwDto) {
        try {
            FindResultDto result = userService.changePassword(changePwDto);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(FindResultDto.builder()
                    .success(false)
                    .message(e.getMessage())
                    .build(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/email")
    ResponseEntity<String> getEmail(@RequestParam String name) {
        try {
            String email = userService.getEmailByName(name);
            return new ResponseEntity<>(email, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }

    @PutMapping("/name")
    ResponseEntity<String> updateName(@RequestBody UpdateNameDto updateNameDto) {
        log.info(updateNameDto.toString());
        String name = userService.updateName(updateNameDto);
        return new ResponseEntity<>(name, HttpStatus.OK);
    }
}
