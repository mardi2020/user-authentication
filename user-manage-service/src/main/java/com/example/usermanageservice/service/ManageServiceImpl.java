package com.example.usermanageservice.service;

import com.example.usermanageservice.client.UserClient;
import com.example.usermanageservice.dto.UserInfo;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ManageServiceImpl implements ManageService {

    private final UserClient userClient;

    @Override
    public List<UserInfo> getUserAll(String token) {
        ResponseEntity<List<UserInfo>> userAll = userClient.getUserAll(token);
        return userAll.getBody();
    }
}
