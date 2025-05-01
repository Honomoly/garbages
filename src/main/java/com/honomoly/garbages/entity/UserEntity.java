package com.honomoly.garbages.entity;

import com.honomoly.garbages.lib.SHA256Tool;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserEntity extends AbstractEntity {

    private Boolean isActive;
    private String identifier;
    private String nickname;
    private String hashSalt;
    private byte[] passwordHash;

    public static byte[] constructPasswordHash(String salt, String password) {
        String input = new StringBuilder("#")
                .append(salt)
                .append("@")
                .append(password)
                .toString();

        return SHA256Tool.getHash(input);
    }

}
