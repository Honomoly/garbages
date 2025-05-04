package com.honomoly.garbages.service;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

@Service
public class ChatService {

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

}
