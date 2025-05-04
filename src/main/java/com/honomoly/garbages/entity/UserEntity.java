package com.honomoly.garbages.entity;

import com.honomoly.garbages.lib.SHA256Lib;

import lombok.Getter;
import lombok.Setter;

/** users */
@Getter
@Setter
public class UserEntity extends AbstractEntity {;

    private String identifier;
    private String nickname;
    private String hashSalt;
    private byte[] passwordHash;

    /**
     * 주어진 hashSalt값과 결합하여 비밀번호의 hash값을 계산
     * @param password
     * @return SHA-256 Hash Code
     */
    public byte[] constructPasswordHash(String password) {
        if (hashSalt == null) return null;

        String input = new StringBuilder("#")
                .append(hashSalt)
                .append("@")
                .append(password)
                .toString();

        return SHA256Lib.getHash(input);
    }

    /** 2자릿수 hashSalt값을 최초로 생성하는 메소드 */
    public void generateHashSalt() {
        this.hashSalt = SHA256Lib.generateRandomCode(2);
    }

}
