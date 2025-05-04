package com.honomoly.garbages.interceptor;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.honomoly.garbages.entity.UserEntity;
import com.honomoly.garbages.lib.JWTLib;
import com.honomoly.garbages.service.ChatService;
import com.honomoly.garbages.service.UserService;

@Component
public class BaseHandshakeInterceptor implements HandshakeInterceptor {

    @Autowired
    private UserService userService;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
            WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {

        List<String> authorizationList = request.getHeaders().get("Authorization");

        if (authorizationList.size() != 1) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return false;
        }

        String authorization = authorizationList.get(0);

        if (!authorization.startsWith("Bearer ")) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return false;
        }

        long usersId;
        try {
            usersId = JWTLib.verifyJWT(authorization.substring(7));
        } catch (Exception e) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return false;
        }

        UserEntity user = userService.selectById(usersId);

        if (user == null) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return false;
        }

        if (ChatService.isAlreadyConnected(usersId)) {
            response.setStatusCode(HttpStatus.CONFLICT);
            return false;
        }

        attributes.put("user", user);

        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
			WebSocketHandler wsHandler, @Nullable Exception exception) {
    }

}
