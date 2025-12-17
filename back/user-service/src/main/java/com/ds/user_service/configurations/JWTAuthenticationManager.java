package com.ds.user_service.configurations;

import com.ds.user_service.repository.UserRepository;
import com.ds.user_service.service.AuthService;
import com.ds.user_service.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
@Component
@RequiredArgsConstructor
public class JWTAuthenticationManager implements ReactiveAuthenticationManager {
    private final JwtService jwtService;
    private final UserRepository repository;
    @Override
    public Mono<Authentication> authenticate(Authentication authentication) throws AuthenticationException {
        String token = authentication.getCredentials().toString();
        String username = jwtService.getUserName(token);

        return repository.findById(username)
                .map(userDetails -> {
                    if (jwtService.validateToken(token)) {
                        return authentication;
                    } else {
                        throw new AuthenticationException("Invalid JWT token") {};
                    }
                });
    }

}
