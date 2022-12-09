package com.mardi2020.userservice.service;

import com.mardi2020.userservice.dto.request.JoinDto;
import com.mardi2020.userservice.dto.response.JoinResultDto;
import com.mardi2020.userservice.repository.UserEntity;
import com.mardi2020.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public JoinResultDto join(JoinDto joinDto) {
        UserEntity user = JoinDto.toEntity(joinDto, passwordEncoder);
        UserEntity savedUser = userRepository.save(user);
        return JoinResultDto.setting(true, savedUser.getEmail() + " SUCCESS");
    }

}
