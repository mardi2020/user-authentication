package com.mardi2020.joinservice.controller;

import com.mardi2020.joinservice.dto.request.JoinDto;
import com.mardi2020.joinservice.dto.response.JoinResultDto;
import com.mardi2020.joinservice.exception.EmailDuplicatedException;
import com.mardi2020.joinservice.exception.NicknameDuplicatedException;
import com.mardi2020.joinservice.exception.PasswordNotValidException;
import com.mardi2020.joinservice.service.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JoinController {
    private final JoinService joinService;

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody JoinDto joinDto) {
        try {
            JoinResultDto join = joinService.join(joinDto);
            return new ResponseEntity<>(join, HttpStatus.OK);
        } catch (EmailDuplicatedException | NicknameDuplicatedException | PasswordNotValidException e) {
            return new ResponseEntity<>(JoinResultDto.setting(false, e.getMessage()), HttpStatus.OK);
        }
    }

    @DeleteMapping("/leave")
    public ResponseEntity<?> leave(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        joinService.leave(token);
        return ResponseEntity.ok("delete user");
    }

}
