package com.mardi2020.findinfoservice.controller;

import com.mardi2020.findinfoservice.dto.request.ChangePwDto;
import com.mardi2020.findinfoservice.dto.response.UserInfoDto;
import com.mardi2020.findinfoservice.service.InfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class InfoController {

    private final InfoService infoService;

    @PatchMapping("/password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePwDto changePwDto) {
        log.error("changePassword - find info " + changePwDto.toString());
        try {
            ResponseEntity<UserInfoDto> result = infoService.findPassword(changePwDto);
            log.error(result.toString());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
