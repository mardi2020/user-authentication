package com.mardi2020.userservice.service;

import com.mardi2020.userservice.dto.request.JoinDto;
import com.mardi2020.userservice.dto.response.JoinResultDto;

public interface UserService {
    JoinResultDto join(JoinDto joinDto);

}
