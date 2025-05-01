package com.honomoly.garbages.service;

import java.util.Arrays;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponseException;

import com.honomoly.garbages.dto.UserDto;
import com.honomoly.garbages.dto.auth.SignInRequest;
import com.honomoly.garbages.dto.auth.SignUpRequest;
import com.honomoly.garbages.entity.UserEntity;

@Service
public class AuthService {

    @Autowired
    private UserService userService;

    public UserDto signUp(SignUpRequest request) {

        // user entity 생성
        UserEntity user = new UserEntity();
        user.setIdentifier(request.identifier());
        user.setNickname(request.nickname());
        Random random = new Random();
        int salt = random.nextInt(90) + 10;
        user.setHashSalt(String.valueOf(salt));
        user.setPasswordHash(UserEntity.constructPasswordHash(user.getHashSalt(), request.password()));

        try {
            userService.insert(user);
        } catch (DuplicateKeyException e) {
            // 키가 중복이지요
            throw new ErrorResponseException(HttpStatus.CONFLICT);
        }

        // TODO : 세션 생성후 처리

        return new UserDto(user);
    }

    public UserDto signIn(SignInRequest request) {

        UserEntity user = userService.selectByIdentifier(request.identifier());

        if (user == null) {
            throw new ErrorResponseException(HttpStatus.UNAUTHORIZED);
        }

        byte[] input = UserEntity.constructPasswordHash(user.getHashSalt(), request.password());

        if (!Arrays.equals(input, user.getPasswordHash())) {
            throw new ErrorResponseException(HttpStatus.UNAUTHORIZED);
        }

        return new UserDto(user);
    }

}
