package com.mardi2020.userservice.service;

import com.mardi2020.userservice.dto.request.JoinDto;
import com.mardi2020.userservice.dto.response.JoinResultDto;
import com.mardi2020.userservice.dto.response.UserDto;
import com.mardi2020.userservice.dto.response.UserInfoDto;
import com.mardi2020.userservice.exception.UserNotFoundException;
import com.mardi2020.userservice.repository.UserEntity;
import com.mardi2020.userservice.repository.UserRepository;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    @Override
    public UserDto getUserByUserId(Long userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(
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
    public List<UserInfoDto> getUserAll() {
        List<UserEntity> users = userRepository.findAll();
        return users.stream().map(UserInfoDto::new).collect(Collectors.toList());
    }

    public static final class CustomUserDetails extends UserEntity implements UserDetails {
        private static final List<GrantedAuthority> ROLE_USER = Collections
                .unmodifiableList(AuthorityUtils.createAuthorityList("ROLE_USER"));

        CustomUserDetails(UserEntity user) {
            super(user.getId(), user.getEmail(), user.getPassword(), user.getName());
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return ROLE_USER;
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
