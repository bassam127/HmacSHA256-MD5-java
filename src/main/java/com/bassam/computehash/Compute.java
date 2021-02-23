package com.bassam.computehash;

import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class Compute {

	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException {
		System.out.println(computeMd5("emma lönberg"));
		System.out.println(computeSha256("bassam", "aldalati"));

	}

	private static String computeMd5(String message) throws NoSuchAlgorithmException {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		byte[] digest = md5.digest(message.getBytes());
		StringBuffer sb = new StringBuffer();

		for (byte b : digest) {
			sb.append(Integer.toHexString((b & 0xFF) | 0x100).substring(1));
		}
		return sb.toString();
	}

	private static String computeSha256(String message, String secret)
			throws NoSuchAlgorithmException, InvalidKeyException {
		byte[] secretKey = secret.getBytes();
		SecretKeySpec signingKey = new SecretKeySpec(secretKey, "HmacSHA256");
		Mac mac = Mac.getInstance("HmacSHA256");
		mac.init(signingKey);
		byte[] stringBytes = message.getBytes(Charset.forName("UTF-8"));
		byte[] hashedValue = mac.doFinal(stringBytes);
		byte[] encode = Base64.getEncoder().encode(hashedValue);

		StringBuilder result = new StringBuilder();

		for (int i = 0; i < hashedValue.length; i++) {
			result.append(Integer.toHexString((hashedValue[i] & 0xff) | 0x100).substring(1));
		}

		return result.toString();

	}

}
