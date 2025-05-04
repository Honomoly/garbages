package com.honomoly.garbages.service;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.honomoly.garbages.entity.ChatEntity;
import com.honomoly.garbages.entity.UserEntity;
import com.honomoly.garbages.mapper.ChatMapper;

@Service
public class ChatService extends AbstractService<ChatEntity> {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ChatMapper chatMapper;

    @Override
    public ChatMapper getMapper() {
        return chatMapper;
    }

    private static final Map<Long, WebSocketSession> connectedUsers = new ConcurrentHashMap<>();

    /**
     * 유저 등록
     * @param username
     * @return
     */
    public static WebSocketSession registerUser(long usersId, WebSocketSession session) throws IOException {
        return connectedUsers.putIfAbsent(usersId, session);
    }

    public static boolean isAlreadyConnected(long usersId) {
        return connectedUsers.containsKey(usersId);
    }

    public static void removeUser(long usersId, String sessionId) throws IOException {
        connectedUsers.computeIfPresent(usersId, (k, v) -> {
            if (v.getId().equals(sessionId))
                return null; // 동일 세션이면 제거
            return v; // 아니면 유지
        });
    }

    public void sendMessage(String message, UserEntity sender) throws JsonProcessingException {

        // 메시지 저장
        ChatEntity chat = new ChatEntity();
        chat.setUsersId(sender.getId());
        chat.setMessage(message);

        insert(chat);

        byte[] payload = objectMapper.writeValueAsBytes(Map.of(
            "identifier", sender.getIdentifier(),
            "nickname", sender.getNickname(),
            "message", chat.getMessage(),
            "timestamp", chat.getCreatedAt().toInstant()
        ));

        TextMessage textMessage = new TextMessage(payload);

        connectedUsers.entrySet().forEach(entry -> {
            if (!entry.getKey().equals(sender.getId())) {
                try {
                    entry.getValue().sendMessage(textMessage);
                } catch (IOException e) {
                    // 예외처리?
                }
            }
        });
    }

}
