package com.example.usermanageservice.service;

import com.example.usermanageservice.dto.UserInfo;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface ManageService {
    List<UserInfo> getUserAll(String token);
}
