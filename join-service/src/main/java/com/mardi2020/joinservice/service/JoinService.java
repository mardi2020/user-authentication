package com.mardi2020.joinservice.service;

import com.mardi2020.joinservice.dto.request.JoinDto;
import com.mardi2020.joinservice.dto.response.JoinResultDto;

public interface JoinService {

    JoinResultDto join(JoinDto joinDto);

    void leave(String token);
}
