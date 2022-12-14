package com.mardi2020.userservice.service;

import com.mardi2020.userservice.dto.request.ChangePwDto;
import com.mardi2020.userservice.dto.request.JoinDto;
import com.mardi2020.userservice.dto.request.UpdateNameDto;
import com.mardi2020.userservice.dto.response.FindResultDto;
import com.mardi2020.userservice.dto.response.JoinResultDto;
import com.mardi2020.userservice.dto.response.UserDto;
import com.mardi2020.userservice.dto.response.UserInfoDto;
import com.mardi2020.userservice.exception.PasswordNotValidException;
import com.mardi2020.userservice.exception.UserNotFoundException;
import com.mardi2020.userservice.repository.UserEntity;
import com.mardi2020.userservice.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.env.Environment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final Environment env;

    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(username);
        if (userEntity == null) {
            throw new UsernameNotFoundException(username);
        }
        return new CustomUserDetails(userEntity);
    }

    @Override
    @Transactional
    public JoinResultDto join(JoinDto joinDto) {
        UserEntity user = JoinDto.toEntity(joinDto, passwordEncoder);
        UserEntity savedUser = userRepository.save(user);
        return JoinResultDto.setting(true, savedUser.getEmail() + " SUCCESS");
    }

    @Cacheable(key = "#id", cacheNames = "user", cacheManager = "cacheManager")
    @Override
    public UserDto getUserByUserId(Long id) {
        UserEntity user = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("[ERROR] USER NOT FOUND")
        );
        return new UserDto(user);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        UserEntity user = userRepository.findByEmail(email);
        return new UserDto(user);
    }

    @Override
    public Long getUserIdByToken(String token) {
        token = token.replace("Bearer ", "");
        try {
            String userId = Jwts.parser()
                    .setSigningKey(env.getProperty("token.secret"))
                    .parseClaimsJws(token).getBody().getSubject();
            return Long.valueOf(userId);
        } catch (Exception e) {
            return null;
        }
    }
    @Cacheable(key = "#id", cacheNames = "user", cacheManager = "cacheManager")
    public UserDto getMyInfo(String token, Long id) {
        Long userId = getUserIdByToken(token);
        if (userId == null) {
            throw new UserNotFoundException("userId??? ?????? ??? ????????????.");
        }
        return getUserByUserId(userId);
    }

    @Override
    public List<UserInfoDto> getUserAll() {
        List<UserEntity> users = userRepository.findAll();
        return users.stream().map(UserInfoDto::new).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public FindResultDto changePassword(ChangePwDto changePwDto) {
        String email = changePwDto.getEmail();
        String name = changePwDto.getName();
        UserEntity user = userRepository.findByEmail(changePwDto.getEmail());
        if (user.getEmail().equals(email) && user.getName().equals(name)) {
            String password = changePwDto.getPassword();
            if (password.length() < 8) {
                throw new PasswordNotValidException("[ERROR] PW NOT VALID");
            }
            user.updatePassword(passwordEncoder.encode(changePwDto.getPassword()));
            userRepository.save(user);

            return FindResultDto.builder()
                    .success(true)
                    .message(email + "CHANGE SUCCESS")
                    .build();
        }
        return FindResultDto.builder()
                .success(false)
                .message("???????????? ?????? ??????")
                .build();
    }

    @Override
    public String getEmailByName(String name) {
        UserEntity user = userRepository.findByName(name);
        if (user.getEmail() == null) {
            throw new UserNotFoundException("[ERROR] USER NOT FOUND");
        }
        return user.getEmail();
    }

    @Override
    public Long getUserIdByName(String name) {
        UserEntity user = userRepository.findByName(name);
        return user.getId();
    }

    @Override
    @Transactional
    @CacheEvict(key = "#id", cacheNames = "user", cacheManager = "cacheManager")
    public Long deleteUser(String token, Long id) {
        if (id == null) {
            throw new UserNotFoundException("userId??? ?????? ??? ????????????.");
        }
        UserEntity user = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("user??? ???????????? ????????????.")
        );
        userRepository.delete(user);
        return id;
    }

    @Override
    @Transactional
    @CacheEvict(key = "#id", cacheNames = "user", cacheManager = "cacheManager")
    public String updateName(UpdateNameDto updateNameDto, Long id) {
        UserEntity user = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("user not found")
        );

        user.updateName(updateNameDto.getName());
        UserEntity saved = userRepository.save(user);
        return saved.getName();
    }

    public static final class CustomUserDetails extends UserEntity implements UserDetails {
        CustomUserDetails(UserEntity user) {
            super(user.getId(), user.getEmail(), user.getPassword(), user.getName(), user.getRoles());
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return Collections
                    .unmodifiableList(AuthorityUtils.createAuthorityList(super.getRoles()));
        }

        @Override
        public String getUsername() {
            return getEmail();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }
}
