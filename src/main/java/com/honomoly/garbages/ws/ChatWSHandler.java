package com.honomoly.garbages.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.honomoly.garbages.service.ChatService;

@Component
public class ChatWSHandler extends TextWebSocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(ChatWSHandler.class);

    @Autowired
    private ChatService chatService;

    @Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String username = (String) session.getAttributes().get("username");
        logger.info("[WebSocket] Connected : " + username);
	}

    @Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String username = (String) session.getAttributes().get("username");
        ChatService.removeUser(username);
        logger.info("[WebSocket] Disconnected : " + username);
	}

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        
    }

}
