package com.mardi2020.findinfoservice.service;

import com.mardi2020.findinfoservice.client.UserServiceClient;
import com.mardi2020.findinfoservice.dto.request.ChangePwDto;
import com.mardi2020.findinfoservice.dto.request.UpdateNameDto;
import com.mardi2020.findinfoservice.dto.response.UserDto;
import com.mardi2020.findinfoservice.dto.response.UserInfoDto;
import com.mardi2020.findinfoservice.exception.EmailNotFoundException;
import com.mardi2020.findinfoservice.exception.NameNotFoundException;
import com.mardi2020.findinfoservice.repository.InfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class InfoServiceImpl implements InfoService {

    private final InfoRepository infoRepository;

    private final UserServiceClient userServiceClient;

    @Override
    public ResponseEntity<UserInfoDto> findPassword(String token, ChangePwDto changePwDto) {
        ResponseEntity<UserInfoDto> res = userServiceClient.changePassword(token, changePwDto);
        if (res.getBody() == null || !res.getBody().getSuccess()) {
            throw new EmailNotFoundException("[ERROR] INFO DOES NOT MATCH");
        }
        return res;
    }

    @Override
    public String findEmail(String name) {
        ResponseEntity<String> result = userServiceClient.getEmail(name);
        String email = result.getBody();
        if (result.getStatusCode() != HttpStatus.OK || email == null) {
            throw new NameNotFoundException("[ERROR] USER NOT FOUND");
        }
        return email;
    }

    @Cacheable(key = "#id", cacheNames = "user", cacheManager = "cacheManager")
    @Override
    public UserDto getUserInfo(String token, Long id) {
        ResponseEntity<UserDto> userInfo = userServiceClient.getUserInfo(token);
        if (userInfo.getStatusCode() != HttpStatus.OK) {
            throw new NameNotFoundException("[ERROR] USER NOT FOUND");
        }
        return userInfo.getBody();
    }

    @CacheEvict(key = "#id", cacheNames = "user", cacheManager = "cacheManager")
    @Override
    public UserInfoDto updateUserName(String token, UpdateNameDto updateNameDto, Long id) {
        ResponseEntity<String> res = userServiceClient.changeName(token, updateNameDto);
        if (res.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("변경 중 예외가 발생했습니다.");
        }
        return UserInfoDto.builder()
                .success(true)
                .message(res.getBody())
                .build();
    }

}
