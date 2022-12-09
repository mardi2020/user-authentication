package com.mardi2020.joinservice.client;

import com.mardi2020.joinservice.dto.request.JoinDto;
import com.mardi2020.joinservice.dto.response.JoinResultDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service", url = "http://localhost:8000")
public interface UserServiceClient {

    @PostMapping("/user-service/users")
    ResponseEntity<JoinResultDto> postUser(@RequestBody JoinDto joinDto);
}
