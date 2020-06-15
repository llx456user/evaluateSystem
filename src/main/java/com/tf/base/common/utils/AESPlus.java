package com.tf.base.common.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESPlus {

	// 加密方式
	private static final String AES_TYPE = "AES/CBC/PKCS5Padding";

	/**
	 * 加密
	 * 
	 * @param key
	 *            密钥(8个字节的倍数)
	 * @param str
	 *            需要加密的字符串
	 * @return String
	 */
	public static String encode(String key, String str) {
		String result = null;
		try {
			Cipher cipher = Cipher.getInstance(AES_TYPE);
			byte[] iv = new byte[16];
			IvParameterSpec ivSpec = new IvParameterSpec(iv);
			cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes(), "AES"), ivSpec);
			return Base64New.encode_str(cipher.doFinal(str.getBytes("UTF-8")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 解密
	 * 
	 * @param key
	 *            密钥(8个字节的倍数)
	 * @param str
	 *            需要解密的字符串
	 * @return String
	 */
	public static String decode(String key, String str) {
		String result = null;
		try {
			Cipher cipher = Cipher.getInstance(AES_TYPE);
			byte[] iv = new byte[16];
			IvParameterSpec ivSpec = new IvParameterSpec(iv);
			cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes(), "AES"), ivSpec);
			return new String(cipher.doFinal(Base64New.decode(str)), "UTF8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
