package com.honomoly.garbages.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.honomoly.garbages.interceptor.BaseHandshakeInterceptor;
import com.honomoly.garbages.ws.ChatWSHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private ChatWSHandler chatWSHandler;

    @Autowired
    private BaseHandshakeInterceptor baseHandshakeInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chatWSHandler, "/ws/chat")
                .setAllowedOrigins("*")
                .addInterceptors(baseHandshakeInterceptor);
    }

}
