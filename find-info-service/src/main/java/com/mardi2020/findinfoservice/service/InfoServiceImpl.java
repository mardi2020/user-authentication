package com.mardi2020.findinfoservice.service;

import com.mardi2020.findinfoservice.client.UserServiceClient;
import com.mardi2020.findinfoservice.dto.request.ChangePwDto;
import com.mardi2020.findinfoservice.dto.response.UserInfoDto;
import com.mardi2020.findinfoservice.exception.EmailNotFoundException;
import com.mardi2020.findinfoservice.repository.InfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InfoServiceImpl implements InfoService {

    private final InfoRepository infoRepository;

    private final UserServiceClient userServiceClient;

    @Override
    public ResponseEntity<UserInfoDto> findPassword(ChangePwDto changePwDto) {
        ResponseEntity<UserInfoDto> res = userServiceClient.changePassword(changePwDto);
        if (res.getBody() == null || !res.getBody().getSuccess()) {
            throw new EmailNotFoundException("[ERROR] INFO DOES NOT MATCH");
        }
        return res;
    }

}
