package com.mardi2020.findinfoservice.client;

import com.mardi2020.findinfoservice.dto.request.ChangePwDto;
import com.mardi2020.findinfoservice.dto.response.UserInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service", url = "http://localhost:8000/user-service")
public interface UserServiceClient {

    @PutMapping("/users/password")
    ResponseEntity<UserInfoDto> changePassword(@RequestBody ChangePwDto changePwDto);

    @GetMapping("/users/email")
    ResponseEntity<String> getEmail(@RequestParam String name);
}
