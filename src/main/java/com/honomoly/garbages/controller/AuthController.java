package com.honomoly.garbages.controller;

import org.springframework.web.bind.annotation.RestController;

import com.honomoly.garbages.dto.UserDto;
import com.honomoly.garbages.dto.auth.SignInRequest;
import com.honomoly.garbages.dto.auth.SignUpRequest;
import com.honomoly.garbages.service.AuthService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @ResponseBody
    @PostMapping("/sign-up")
    public UserDto signUp(@RequestBody @Valid SignUpRequest request) {
        return authService.signUp(request);
    }

    @ResponseBody
    @PostMapping("/sign-in")
    public UserDto signIn(@RequestBody @Valid SignInRequest request) {
        return authService.signIn(request);
    }

}
