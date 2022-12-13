package com.mardi2020.userservice.service;

import com.mardi2020.userservice.dto.request.ChangePwDto;
import com.mardi2020.userservice.dto.request.JoinDto;
import com.mardi2020.userservice.dto.response.FindResultDto;
import com.mardi2020.userservice.dto.response.JoinResultDto;
import com.mardi2020.userservice.dto.response.UserDto;
import com.mardi2020.userservice.dto.response.UserInfoDto;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    JoinResultDto join(JoinDto joinDto);
    UserDto getUserByUserId(Long userId);
    UserDto getUserByEmail(String userName);
    List<UserInfoDto> getUserAll();

    FindResultDto changePassword(ChangePwDto changePwDto);
}
