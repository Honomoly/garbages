package com.honomoly.garbages.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.honomoly.garbages.GlobalHttpException;
import com.honomoly.garbages.dto.UserDto;
import com.honomoly.garbages.dto.auth.SignInRequest;
import com.honomoly.garbages.dto.auth.SignUpRequest;
import com.honomoly.garbages.entity.UserEntity;
import com.honomoly.garbages.lib.JWTLib;

@Service
public class AuthService {

    @Autowired
    private UserService userService;

    public UserDto signUp(SignUpRequest request) {

        // user entity 생성
        UserEntity user = new UserEntity();
        user.setIdentifier(request.identifier());
        user.setNickname(request.nickname());
        user.generateHashSalt();
        user.setPasswordHash(user.constructPasswordHash(request.password()));

        try {
            userService.insert(user);
        } catch (DuplicateKeyException e) {
            // 키가 중복이지요
            throw new GlobalHttpException(HttpStatus.CONFLICT, "Duplicated identifier.");
        }

        String token = JWTLib.generateJWT(user.getId());

        return new UserDto(user, token);
    }

    public UserDto signIn(SignInRequest request) {

        UserEntity user = userService.selectByIdentifier(request.identifier());

        if (user == null)
            throw new GlobalHttpException(HttpStatus.UNAUTHORIZED, "Sign in fail.");

        byte[] input = user.constructPasswordHash(request.password());

        if (!Arrays.equals(input, user.getPasswordHash()))
            throw new GlobalHttpException(HttpStatus.UNAUTHORIZED, "Sign in fail.");

        String token = JWTLib.generateJWT(user.getId());

        return new UserDto(user, token);
    }

}
