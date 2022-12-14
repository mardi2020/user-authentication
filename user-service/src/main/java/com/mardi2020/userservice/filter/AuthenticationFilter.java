package com.mardi2020.userservice.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mardi2020.userservice.dto.request.LoginDto;
import com.mardi2020.userservice.dto.response.UserDto;
import com.mardi2020.userservice.service.UserService;
import com.mardi2020.userservice.service.UserServiceImpl.CustomUserDetails;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final UserService userService;

    private final Environment env;

    public AuthenticationFilter(AuthenticationManager authenticationManager,
                                UserService userService,
                                Environment env) {
        super.setAuthenticationManager(authenticationManager);
        this.userService = userService;
        this.env = env;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        String email = ((CustomUserDetails) authResult.getPrincipal()).getUsername();
        UserDto user = userService.getUserByEmail(email);
        String token = Jwts.builder()
                .setSubject(user.getUserId().toString())
                .setExpiration(new Date(System.currentTimeMillis() +
                        Long.parseLong(env.getProperty("token.expiration_time"))))
                .signWith(SignatureAlgorithm.HS512, env.getProperty("token.secret"))
                .compact();

        response.addHeader("token", token);
        response.addHeader("userId", user.getUserId().toString());
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            LoginDto creds = new ObjectMapper().readValue(request.getInputStream(), LoginDto.class);
            UserDetails user = userService.loadUserByUsername(creds.getEmail());
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(user, creds.getPassword(), user.getAuthorities()));
        } catch (Exception e) {
            throw new RuntimeException("[ERROR] LOGIN FAIL");
        }
    }
}