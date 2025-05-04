package com.honomoly.garbages.ws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.honomoly.garbages.entity.UserEntity;
import com.honomoly.garbages.service.ChatService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ChatWSHandler extends TextWebSocketHandler {

    @Autowired
    private ChatService chatService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // Hand Shake에서 저장한 가져온 유저 정보 추출하여 저장
        UserEntity user = (UserEntity) session.getAttributes().get("user");

        if (ChatService.registerUser(user.getId(), session) != null) {
            session.close(CloseStatus.SERVICE_OVERLOAD);
            return;
        }

        String info = new StringBuilder("[WebSocket] Connected : ")
                .append(user.getNickname())
                .append(" (ID : ").append(user.getId()).append(")")
                .toString();

        log.info(info);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        UserEntity user = (UserEntity) session.getAttributes().get("user");

        ChatService.removeUser(user.getId(), session.getId());

        if (CloseStatus.NORMAL.equals(status)) {
            String info = new StringBuilder("[WebSocket] Disconnected : ")
                    .append(user.getNickname())
                    .append(" (ID : ").append(user.getId()).append(")")
                    .toString();

            log.info(info);
        } else
            log.error("[WebSocket] Connection Error");
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

    }

}
