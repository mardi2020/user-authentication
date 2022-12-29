package com.mardi202.apigateway.filter;

import com.mardi202.apigateway.util.JwtUtils;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

    private final Environment env;

    private final JwtUtils jwtUtils;

    public AuthorizationHeaderFilter(Environment env, JwtUtils jwtUtils) {
        super(Config.class);
        this.jwtUtils = jwtUtils;
        this.env = env;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            log.info("Request Headers: " + request.getHeaders());
            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, "No token header");
            }
            String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            String token = authorizationHeader.replace("Bearer", "");

            if (!isJwtValid(token)) {
                return onError(exchange, "JWT not valid");
            }
            String subject = jwtUtils.getUserId(token);
            ServerHttpRequest req = request.mutate().header("user-id", subject).build();

            return chain.filter(exchange.mutate().request(req).build());
        });
    }

    private Mono<Void> onError(ServerWebExchange exchange, String message) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        log.error(message);
        return response.setComplete();
    }

    private boolean isJwtValid(String token) {
        boolean result = true;
        String subject = null;
        try {
            subject = Jwts.parser()
                    .setSigningKey(env.getProperty("token.secret"))
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            result = false;
        }
        if (subject == null || subject.isEmpty()) {
            result = false;
        }
        return result;
    }

    public static class Config {

    }
}
