package com.mardi2020.userservice.controller;

import com.mardi2020.userservice.dto.request.ChangePwDto;
import com.mardi2020.userservice.dto.request.JoinDto;
import com.mardi2020.userservice.dto.response.FindResultDto;
import com.mardi2020.userservice.dto.response.JoinResultDto;
import com.mardi2020.userservice.dto.response.UserDto;
import com.mardi2020.userservice.dto.response.UserInfoDto;
import com.mardi2020.userservice.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private final Environment env;

    @GetMapping("/status")
    public String status() {
        return String.format("It is working in User service on PORT %s", env.getProperty("local.server.port"));
    }

    @PostMapping
    public ResponseEntity<JoinResultDto> joinUser(@RequestBody JoinDto joinDto) {
        try {
            JoinResultDto result = userService.join(joinDto);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(JoinResultDto.setting(false, "SIGN UP FAILED"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/info")
    public ResponseEntity<?> getUserInfo(@RequestParam String email) {
        try {
            UserDto user = userService.getUserByEmail(email);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getUserAll() {
        try {
            List<UserInfoDto> userAll = userService.getUserAll();
            return new ResponseEntity<>(userAll, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
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
}
