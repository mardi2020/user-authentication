package com.mardi2020.joinservice.controller;

import com.mardi2020.joinservice.dto.request.JoinDto;
import com.mardi2020.joinservice.dto.response.JoinResultDto;
import com.mardi2020.joinservice.exception.EmailDuplicatedException;
import com.mardi2020.joinservice.exception.NicknameDuplicatedException;
import com.mardi2020.joinservice.exception.PasswordNotValidException;
import com.mardi2020.joinservice.service.JoinService;
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
@RequestMapping("/join")
public class JoinController {
    private final JoinService joinService;

    private final Environment env;

    @PostMapping
    public ResponseEntity<?> join(@RequestBody JoinDto joinDto) {
        try {
            JoinResultDto join = joinService.join(joinDto);
            return new ResponseEntity<>(join, HttpStatus.OK);
        } catch (EmailDuplicatedException | NicknameDuplicatedException | PasswordNotValidException e) {
            return new ResponseEntity<>(JoinResultDto.setting(false, e.getMessage()), HttpStatus.OK);
        }
    }

    @GetMapping("/health_check")
    public String status() {
        return String.format("It is working in User service on PORT %s", env.getProperty("local.server.port"));
    }
}
