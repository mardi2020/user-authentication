package com.mardi2020.findinfoservice.service;

import com.mardi2020.findinfoservice.dto.request.ChangePwDto;
import com.mardi2020.findinfoservice.dto.response.UserInfoDto;
import org.springframework.http.ResponseEntity;

public interface InfoService {
    ResponseEntity<UserInfoDto> findPassword(ChangePwDto changePwDto);
}
