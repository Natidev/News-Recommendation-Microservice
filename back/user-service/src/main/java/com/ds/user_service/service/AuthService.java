package com.ds.user_service.service;

import com.ds.user_service.exceptions.UserAlreadyExistsException;
import com.ds.user_service.model.UserMapper;
import com.ds.user_service.model.dto.UserRequest;
import com.ds.user_service.model.dto.UserResponse;
import com.ds.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtService  jwtService;
    private final UserRepository repository;
    private final UserMapper mapper;

    public  UserResponse createUser(UserRequest user) {
        if(repository.existsById(user.username()))
            throw new UserAlreadyExistsException(String.format("A user with the username: %s already exists",user.username()));
        return mapper
                .toUserResponse(
                        repository.save(mapper.toUser(user))
                );
    }
}
