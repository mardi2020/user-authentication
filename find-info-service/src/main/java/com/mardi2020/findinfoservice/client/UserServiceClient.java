package com.mardi2020.findinfoservice.client;

import com.mardi2020.findinfoservice.dto.request.ChangePwDto;
import com.mardi2020.findinfoservice.dto.request.UpdateNameDto;
import com.mardi2020.findinfoservice.dto.response.UserDto;
import com.mardi2020.findinfoservice.dto.response.UserInfoDto;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service", url = "http://localhost:8000/user-service")
public interface UserServiceClient {

    @PutMapping("/users/password")
    @Headers("Authorization: {token}")
    ResponseEntity<UserInfoDto> changePassword(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                               @RequestBody ChangePwDto changePwDto);

    @PutMapping("/users/name")
    @Headers("Authorization: {token}")
    ResponseEntity<String> changeName(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                      @RequestBody UpdateNameDto updateNameDto);

    @GetMapping("/users/email")
    ResponseEntity<String> getEmail(@RequestParam String name);

    @GetMapping("/users")
    @Headers("Authorization: {token}")
    ResponseEntity<UserDto> getUserInfo(@RequestHeader(HttpHeaders.AUTHORIZATION) String token);
}
