package com.spring.common.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AES {
	
	private static final Logger logger = LoggerFactory.getLogger(AES.class);
	/**
	 * 加密
	 * 
	 * @param content
	 * 需要加密的内容
	 * @param password
	 * 加密密码
	 * @return
	 */
	public static byte[] encrypt(String content, String password) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			//Updated by Alex.Yu  on 2016.10.28
			//bug加密的密钥在Linux下随机生成
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");  
			secureRandom.setSeed(password.getBytes());   
			kgen.init(128, secureRandom);
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			byte[] byteContent = content.getBytes("utf-8");
			cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(byteContent);
			return result; // 加密
		} catch (NoSuchAlgorithmException e) {
			logger.error("NoSuchAlgorithmException");
		} catch (NoSuchPaddingException e) {
			logger.error("NoSuchPaddingException");
		} catch (InvalidKeyException e) {
			logger.error("InvalidKeyException");
		} catch (UnsupportedEncodingException e) {
			logger.error("UnsupportedEncodingException");
		} catch (IllegalBlockSizeException e) {
			logger.error("IllegalBlockSizeException");
		} catch (BadPaddingException e) {
			logger.error("BadPaddingException");
		}
		return null;
	}

	/**
	 * 解密
	 * 
	 * @param content
	 * 待解密内容
	 * @param password
	 * 解密密钥
	 * @return
	 */
	public static byte[] decrypt(byte[] content, String password) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			//Updated by Alex.Yu  on 2016.10.28
			//bug加密的密钥在Linux下随机生成
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");  
			secureRandom.setSeed(password.getBytes());   
			kgen.init(128, secureRandom);
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(content);
			return result; // 加密
		} catch (NoSuchAlgorithmException e) {
			logger.error("NoSuchAlgorithmException");
		} catch (NoSuchPaddingException e) {
			logger.error("NoSuchPaddingException");
		} catch (InvalidKeyException e) {
			logger.error("InvalidKeyException");
		} catch (IllegalBlockSizeException e) {
			logger.error("IllegalBlockSizeException");
		} catch (BadPaddingException e) {
			logger.error("BadPaddingException");
		}
		return null;
	}

	/**
	 * 将二进制转换成16进制
	 * 
	 * @param buf
	 * @return
	 */
	public static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * 将16进制转换为二进制
	 * 
	 * @param hexStr
	 * @return
	 */
	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

	/**
	 * 加密
	 * 
	 * @param content
	 * 需要加密的内容
	 * @param password
	 * 加密密码
	 * @return
	 */
	public static byte[] encrypt2(String content, String password) {
		try {
			SecretKeySpec key = new SecretKeySpec(password.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
			byte[] byteContent = content.getBytes("utf-8");
			cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(byteContent);
			return result; // 加密
		} catch (NoSuchAlgorithmException e) {
			logger.error("NoSuchAlgorithmException");
		} catch (NoSuchPaddingException e) {
			logger.error("NoSuchPaddingException");
		} catch (InvalidKeyException e) {
			logger.error("InvalidKeyException");
		} catch (UnsupportedEncodingException e) {
			logger.error("UnsupportedEncodingException");
		} catch (IllegalBlockSizeException e) {
			logger.error("IllegalBlockSizeException");
		} catch (BadPaddingException e) {
			logger.error("BadPaddingException");
		}
		return null;
	}
	
	/**
	 * 加密
	 * 
	 * @param content
	 * 需要加密的内容
	 * @param password
	 * 加密密码
	 * @return
	 */
	public static byte[] encrypt3(String content, String password) {
		try {
			SecretKeySpec key = new SecretKeySpec(password.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES");
			byte[] byteContent = content.getBytes("utf-8");
			cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(byteContent);
			return result; // 加密
		} catch (NoSuchAlgorithmException e) {
			logger.error("NoSuchAlgorithmException");
		} catch (NoSuchPaddingException e) {
			logger.error("NoSuchPaddingException");
		} catch (InvalidKeyException e) {
			logger.error("InvalidKeyException");
		} catch (UnsupportedEncodingException e) {
			logger.error("UnsupportedEncodingException");
		} catch (IllegalBlockSizeException e) {
			logger.error("IllegalBlockSizeException");
		} catch (BadPaddingException e) {
			logger.error("BadPaddingException");
		}
		return null;
	}

	public static String decrypt2(byte[] bytes, String password) {
		try {
			SecretKeySpec key = new SecretKeySpec(password.getBytes(), "AES");
			Cipher cp = Cipher.getInstance("AES");
			cp.init(Cipher.DECRYPT_MODE, key);
			byte [] ptext = cp.doFinal(bytes);
			return new String(ptext, "UTF8");
		} catch (NoSuchAlgorithmException e) {
			logger.error("NoSuchAlgorithmException");
		} catch (NoSuchPaddingException e) {
			logger.error("NoSuchPaddingException");
		} catch (InvalidKeyException e) {
			logger.error("InvalidKeyException");
		} catch (UnsupportedEncodingException e) {
			logger.error("UnsupportedEncodingException");
		} catch (IllegalBlockSizeException e) {
			logger.error("IllegalBlockSizeException");
		} catch (BadPaddingException e) {
			logger.error("BadPaddingException");
		}
		return null;
	}
}