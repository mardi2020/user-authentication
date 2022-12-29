package com.mardi2020.userservice.service;

import com.mardi2020.userservice.dto.request.ChangePwDto;
import com.mardi2020.userservice.dto.request.JoinDto;
import com.mardi2020.userservice.dto.request.UpdateNameDto;
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

    Long getUserIdByToken(String token);

    List<UserInfoDto> getUserAll();

    UserDto getMyInfo(String token, Long id);

    FindResultDto changePassword(ChangePwDto changePwDto);

    String getEmailByName(String name);

    Long getUserIdByName(String name);

    Long deleteUser(String token, Long id);

    String updateName(UpdateNameDto updateNameDto, Long id);
}
