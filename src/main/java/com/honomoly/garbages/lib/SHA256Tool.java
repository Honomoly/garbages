package com.honomoly.garbages.lib;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256Tool {

    /**
     * 매 실행시에 새로운 다이제스트 객체 생성
     * @return MessageDigest
     */
    private static MessageDigest getSHA256Digest() {
        try {
			return MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			// SHA-256 알고리즘이 없을리가...
			throw new RuntimeException("SHA-256 Algorithm not found.", e);
		}
    }

    /**
     * 해당 입력값으로부터 해시값을 계산
     * @param input
     * @return
     */
    public static byte[] getHash(String input) {

        MessageDigest digest = getSHA256Digest();

        return digest.digest(input.getBytes());
    }

    private static final int RANDOM_CODE_LENGTH = 8;
	private static final BigInteger MODULO = BigInteger.valueOf(62);
	private static final String BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    /**
	 * 특정 입력을 받아 랜덤한 8자리 문자열을 생성, Base62 인코딩 기반
	 * @param input : 시드가 되는 입력
	 * @return randomCode : 8자리의 경우 약 218조개의 가짓수가 가능
	 */
	public static String generateRandomCode() {

        MessageDigest digest = getSHA256Digest();

        String seed = "s" + System.nanoTime();

		byte[] hash = digest.digest(seed.getBytes());
		BigInteger num = new BigInteger(1, hash); // 해시값을 통한 거대한 랜덤 정수 생성

		// div, mod 기법을 통한 랜덤값 추출
		StringBuilder sb = new StringBuilder();

		while (sb.length() < RANDOM_CODE_LENGTH) {
			BigInteger[] divMod = num.divideAndRemainder(MODULO);
			sb.append(BASE62.charAt(divMod[1].intValue()));
			num = divMod[0];
		}

		return sb.toString();
	}

}
