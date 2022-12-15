package com.example.usermanageservice.controller;

import com.example.usermanageservice.dto.UserInfo;
import com.example.usermanageservice.service.ManageService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class ManageController {

    private final ManageService manageService;

    @GetMapping
    public ResponseEntity<?> getUserAll(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        List<UserInfo> users = manageService.getUserAll(token);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
