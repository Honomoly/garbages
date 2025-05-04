package com.honomoly.garbages.lib;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256Lib {

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


	// SHA-256이 생성할 수 있는 Base62 규격은 최대 약 43자리
    private static final int RANDOM_CODE_MAX_LENGTH = 43;
	private static final BigInteger MODULO = BigInteger.valueOf(62);
	private static final String BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    /**
	 * 특정 입력을 받아 랜덤한 문자열을 생성, Base62 인코딩 기반
	 * @param input : 시드가 되는 입력
	 * @return Random Code
	 */
	public static String generateRandomCode(int randomCodeLength) {

		if (randomCodeLength < 1 || randomCodeLength > RANDOM_CODE_MAX_LENGTH)
			randomCodeLength = RANDOM_CODE_MAX_LENGTH;

        MessageDigest digest = getSHA256Digest();

        String seed = "s" + System.nanoTime();

		byte[] hash = digest.digest(seed.getBytes());
		BigInteger num = new BigInteger(1, hash); // 해시값을 통한 거대한 랜덤 정수 생성

		// div, mod 기법을 통한 랜덤값 추출
		StringBuilder sb = new StringBuilder();

		while (sb.length() < randomCodeLength) {
			BigInteger[] divMod = num.divideAndRemainder(MODULO);
			sb.append(BASE62.charAt(divMod[1].intValue()));
			num = divMod[0];
		}

		return sb.toString();
	}

	/** 아무 입력이 없으면 기본 77자리가 생성 */
	public static String generateRandomCode() {
		return generateRandomCode(0);
	}

}
