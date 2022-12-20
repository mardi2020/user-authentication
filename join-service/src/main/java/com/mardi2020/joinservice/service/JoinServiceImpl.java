package com.mardi2020.joinservice.service;

import com.mardi2020.joinservice.client.UserServiceClient;
import com.mardi2020.joinservice.dto.request.JoinDto;
import com.mardi2020.joinservice.dto.response.JoinResultDto;
import com.mardi2020.joinservice.exception.EmailDuplicatedException;
import com.mardi2020.joinservice.exception.NicknameDuplicatedException;
import com.mardi2020.joinservice.exception.PasswordNotValidException;
import com.mardi2020.joinservice.repository.Join;
import com.mardi2020.joinservice.repository.JoinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JoinServiceImpl implements JoinService {

    private final JoinRepository joinRepository;

    private final UserServiceClient userServiceClient;

    @Override
    public JoinResultDto join(JoinDto joinDto) {
        if (joinRepository.existsById(joinDto.getEmail())) {
            throw new EmailDuplicatedException("[ERROR] EMAIL DUPLICATED");
        }
        if (joinRepository.existsById(joinDto.getName())) {
            throw new NicknameDuplicatedException("[ERROR] NAME DUPLICATED");
        }
        String password = joinDto.getPassword();
        if (password.length() < 8) {
            throw new PasswordNotValidException("[ERROR] PASSWORD LENGTH MORE THAN 8");
        }

        joinRepository.save(new Join(joinDto.getEmail(), "EMAIL"));
        joinRepository.save(new Join(joinDto.getName(), "NAME"));

        ResponseEntity<JoinResultDto> response = userServiceClient.postUser(joinDto);
        return response.getBody();
    }

    @Override
    public void leave(String token) {
        ResponseEntity<String> result = userServiceClient.deleteUser(token);

        if (result.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("회원 탈퇴에 실패했습니다.");
        }
    }
}
