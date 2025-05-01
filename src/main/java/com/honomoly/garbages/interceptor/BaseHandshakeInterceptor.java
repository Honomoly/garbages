package com.honomoly.garbages.interceptor;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.util.UriComponentsBuilder;

import com.honomoly.garbages.service.ChatService;

@Component
public class BaseHandshakeInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
            WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {

        List<String> usernameList = UriComponentsBuilder.fromUri(request.getURI()).build()
                .getQueryParams()
                .get("username");

        if (usernameList.size() != 1) {
            response.setStatusCode(HttpStatus.BAD_REQUEST);
            return false;
        }

        String username = usernameList.get(0);

        if (ChatService.registerUser(username) != null) {
            response.setStatusCode(HttpStatus.CONFLICT);
            return false;
        }

        attributes.put("username", username);
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
			WebSocketHandler wsHandler, @Nullable Exception exception) {
    }

}
