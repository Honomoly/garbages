package com.honomoly.garbages.aop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.honomoly.garbages.GlobalHttpException;
import com.honomoly.garbages.lib.JWTLib;

import jakarta.servlet.http.HttpServletRequest;

@Aspect
@Component
public class UserSessionAspect {

    @Autowired
    private HttpServletRequest request;

    @Around("@annotation(com.honomoly.garbages.aop.annotation.UserSession)")
    public Object checkUserSession(ProceedingJoinPoint joinPoint) throws Throwable {

        String authorization = request.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith("Bearer "))
            throw new GlobalHttpException(HttpStatus.UNAUTHORIZED, "Invalid bearer token.");

        long usersId = JWTLib.verifyJWT(authorization.substring(7));

        request.setAttribute("users_id", usersId);

        return joinPoint.proceed();
    }

}
