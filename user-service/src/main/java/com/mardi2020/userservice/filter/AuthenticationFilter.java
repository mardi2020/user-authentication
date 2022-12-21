package com.mardi2020.userservice.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mardi2020.userservice.dto.request.LoginDto;
import com.mardi2020.userservice.dto.response.UserDto;
import com.mardi2020.userservice.service.RefreshTokenService;
import com.mardi2020.userservice.service.UserService;
import com.mardi2020.userservice.service.UserServiceImpl.CustomUserDetails;
import com.mardi2020.userservice.util.CookieUtils;
import com.mardi2020.userservice.util.JwtUtils;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final UserService userService;

    private final RefreshTokenService refreshTokenService;

    private final Environment env;

    public AuthenticationFilter(AuthenticationManager authenticationManager, UserService userService,
                                RefreshTokenService refreshTokenService, Environment env) {
        super(authenticationManager);
        this.userService = userService;
        this.refreshTokenService = refreshTokenService;
        this.env = env;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        CookieUtils cookieUtils = new CookieUtils(env);
        JwtUtils jwtUtils = new JwtUtils(env);

        String email = ((CustomUserDetails) authResult.getPrincipal()).getUsername();
        UserDto user = userService.getUserByEmail(email);
        String accessToken = jwtUtils.generateAccessToken(user.getUserId(), user.getRole());
        String refreshToken = jwtUtils.generateRefreshToken();

        log.info("Access token : " + accessToken);
        log.info("Refresh token : " + refreshToken);

        refreshTokenService.updateRefreshToken(user.getUserId(), jwtUtils.getRefreshTokenId(refreshToken));

        ResponseCookie resCookie = cookieUtils.createRefreshTokenCookie(refreshToken);
        Cookie cookie = cookieUtils.of(resCookie);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.addCookie(cookie);

        Map<String, Object> token = Map.of(
                "access-token", accessToken,
                "expired-time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                        .format(jwtUtils.getExpiredTime(accessToken))
        );

        new ObjectMapper().writeValue(response.getOutputStream(), token);
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