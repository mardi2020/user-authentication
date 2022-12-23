package com.mardi2020.findinfoservice.controller;

import com.mardi2020.findinfoservice.dto.request.ChangePwDto;
import com.mardi2020.findinfoservice.dto.request.UpdateNameDto;
import com.mardi2020.findinfoservice.dto.response.UserDto;
import com.mardi2020.findinfoservice.dto.response.UserInfoDto;
import com.mardi2020.findinfoservice.service.InfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class InfoController {

    private final InfoService infoService;

    @PatchMapping("/password")
    public ResponseEntity<?> changePassword(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                            @RequestBody ChangePwDto changePwDto) {
        try {
            ResponseEntity<UserInfoDto> result = infoService.findPassword(token, changePwDto);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/name")
    public ResponseEntity<?> changeName(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                        @RequestBody UpdateNameDto updateNameDto) {
        try {
            UserInfoDto result = infoService.updateUserName(token, updateNameDto, updateNameDto.getId());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/email")
    public ResponseEntity<?> getMyEmail(@RequestParam String name) {
        log.error("find info - " + name);
        try {
            String email = infoService.findEmail(name);
            return new ResponseEntity<>(email, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/id")
    public ResponseEntity<?> getIdByName(@RequestParam String name) {
        log.error("find info - " + name);
        try {
            String email = infoService.findId(name);
            return new ResponseEntity<>(email, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserInfoById(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable Long id) {
        UserDto userInfo = infoService.getUserInfoById(token, id);
        return new ResponseEntity<>(userInfo, HttpStatus.OK);
    }

    @GetMapping("/my/{id}")
    public ResponseEntity<UserDto> getMyInfo(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                             @PathVariable Long id) {
        UserDto userInfo = infoService.getUserInfo(token, id);
        return new ResponseEntity<>(userInfo, HttpStatus.OK);
    }
}
