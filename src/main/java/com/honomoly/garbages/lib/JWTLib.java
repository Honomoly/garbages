package com.honomoly.garbages.lib;

import java.time.Instant;

import org.springframework.http.HttpStatus;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.honomoly.garbages.GlobalHttpException;

public class JWTLib {

    /** Secret Key를 기준으로 해시 알고리즘 적용 */
    private static final Algorithm algorithm = Algorithm.HMAC256(System.getProperty("JWT_KEY"));
    private static final JWTVerifier verifier = JWT.require(algorithm).build();

    /**
     * 해당 유저ID를 받아 토큰 생성
     * @param usersId
     * @return token
     */
    public static String generateJWT(long usersId) {
        Instant now = Instant.now();

        return JWT.create()
                .withIssuer("garbages-back") // 서버명
                .withSubject("id" + usersId)
                .withAudience("user")
                .withIssuedAt(now)
                .withExpiresAt(now.plusSeconds(3 * 3600L)) // 만료시간은 3시간
                .sign(algorithm);
    }

    /**
     * 해당 토큰을 받아 검증 및 유저ID 추출
     * @param token
     * @return usersId
     */
    public static long verifyJWT(String token) {
        DecodedJWT decodedJWT;

        try {
            decodedJWT = verifier.verify(token);
        } catch (Exception e) {
            // 필요하다면 예외처리 세분화
            throw new GlobalHttpException(HttpStatus.UNAUTHORIZED, "Invalid token.", e);
        }

        String subject = decodedJWT.getSubject();

        if (!subject.startsWith("id"))
            throw new GlobalHttpException(HttpStatus.UNAUTHORIZED, "Token format error - 1.");

        try {
            return Long.parseLong(subject.substring(2));
        } catch (Exception e) {
            throw new GlobalHttpException(HttpStatus.UNAUTHORIZED, "Token format error - 2.");
        }
    }

}
