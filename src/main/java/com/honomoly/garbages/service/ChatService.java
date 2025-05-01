package com.honomoly.garbages.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private static final Map<String, Long> currentUsers = new ConcurrentHashMap<>();

    /**
     * 유저 등록
     * @param username
     * @return 성공시 null, 실패시 이미 등록된 유저의 마지막 로그인 시간 반환
     */
    public static Long registerUser(String username) {
        return currentUsers.putIfAbsent(username, System.currentTimeMillis());
    }

    public static void removeUser(String username) {
        currentUsers.remove(username);
    }

    public static Long getLastLogin(String username) {
        return currentUsers.get(username);
    }
}
