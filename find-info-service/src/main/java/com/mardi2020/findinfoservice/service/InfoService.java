package com.mardi2020.findinfoservice.service;

import com.mardi2020.findinfoservice.dto.request.ChangePwDto;
import com.mardi2020.findinfoservice.dto.request.UpdateNameDto;
import com.mardi2020.findinfoservice.dto.response.UserDto;
import com.mardi2020.findinfoservice.dto.response.UserInfoDto;
import org.springframework.http.ResponseEntity;

public interface InfoService {
    ResponseEntity<UserInfoDto> findPassword(String token, ChangePwDto changePwDto);

    String findEmail(String name);

    UserDto getUserInfo(String token, Long id);

    UserInfoDto updateUserName(String token, UpdateNameDto updateNameDto, Long id);
}
