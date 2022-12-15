package com.example.usermanageservice.client;

import com.example.usermanageservice.dto.UserInfo;
import feign.Headers;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "user-service", url = "http://localhost:8000")
public interface UserClient {

    @GetMapping("/user-service/users/all")
    @Headers("Authorization: {token}")
    ResponseEntity<List<UserInfo>> getUserAll(@RequestHeader(HttpHeaders.AUTHORIZATION) String token);
}
