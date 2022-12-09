package com.mardi2020.userservice.controller;

import com.mardi2020.userservice.dto.request.JoinDto;
import com.mardi2020.userservice.dto.response.JoinResultDto;
import com.mardi2020.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private final Environment env;

    @GetMapping("/health_check")
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
}
