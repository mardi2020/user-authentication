package com.mardi202.apigateway.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RoleFilter extends AbstractGatewayFilterFactory<RoleFilter.Config> {

    private final Environment env;

    public RoleFilter(Environment env) {
        super(Config.class);
        this.env = env;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            String authorizationHeader = Objects
                    .requireNonNull(request.getHeaders().get(HttpHeaders.AUTHORIZATION)).get(0);
            String token = authorizationHeader.replace("Bearer", "");
            String role = jwtParseRole(token);
            if (role == null || !role.equals("ROLE_ADMIN")) {
                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }

            return chain.filter(exchange);
        };
    }

    private String jwtParseRole(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(env.getProperty("token.secret"))
                    .parseClaimsJws(token);

            return claims.getBody().get("role").toString();
        } catch (Exception e) {
            return null;
        }
    }

    public static class Config {
    }

}
