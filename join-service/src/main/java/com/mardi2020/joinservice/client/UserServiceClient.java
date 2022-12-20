package com.mardi2020.joinservice.client;

import com.mardi2020.joinservice.dto.request.JoinDto;
import com.mardi2020.joinservice.dto.response.JoinResultDto;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "user-service", url = "http://localhost:8000")
public interface UserServiceClient {

    @PostMapping("/user-service/users")
    ResponseEntity<JoinResultDto> postUser(@RequestBody JoinDto joinDto);

    @DeleteMapping("/user-service/users")
    @Headers("Authorization: {token}")
    ResponseEntity<String> deleteUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String token);
}
