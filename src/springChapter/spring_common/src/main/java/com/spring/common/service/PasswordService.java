package com.spring.common.service;

import java.nio.charset.StandardCharsets;

import com.spring.common.util.MD5Util;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.common.mq.MqQueueSender;
import com.spring.common.util.AES;
import org.springframework.util.Assert;

/**
 * @author chenhaiyan
 *
 */

@Service
public class PasswordService {
	private static final Logger logger = LoggerFactory.getLogger(PasswordService.class);
	
	private static final int SALT_LENGTH = 15;

	private static final String VERSION = "$4a$";

	private static final String ASE_SECRET_KEY = "U1MjU3SD9.#2Q1YUKL99dellllkk";

	@Autowired
	private RandomStringService randomStringService;

	public String hashPassword(String password) {
		if (StringUtils.isEmpty(password)) {
			throw new IllegalArgumentException("password cannot be empty");
		}
		logger.info("inputted md5 password(hashPassword) is:" + password);
//		String salt = randomStringService.generate(SALT_LENGTH);
		String salt = RandomStringUtils.randomAlphabetic(SALT_LENGTH).toLowerCase();
		String pwd = salt + password;
		// updated by Alex.Yu
		// 新系统密码加密改成aes(salt+md5(plain text))
		// byte[] hash =
		// DigestUtils.sha512(pwd.getBytes(StandardCharsets.UTF_8));
		byte[] hash = AES.encrypt(pwd, ASE_SECRET_KEY);
		StringBuilder builder = new StringBuilder();
		builder.append(VERSION);
		builder.append(SALT_LENGTH).append('$');
		builder.append(salt).append('.').append(Base64.encodeBase64String(hash));
		logger.info("encrypted aes password is:" + builder.toString());
		return builder.toString();
	}

	public boolean checkPassword(String password, String hashed) {
		if (StringUtils.isEmpty(hashed)) {
			return false;
		}
		logger.info("inputted md5 password(checkPassword) is:" + password);
		logger.info("persisted password with version is:" + hashed);
		if (hashed.startsWith("$2a$")) {
			return BCrypt.checkpw(password, hashed);
		}
		String hash = null;
		String pwdhash = "";
		if (hashed.startsWith("$3b$") || hashed.startsWith(VERSION)) {

			int lastIndex = hashed.lastIndexOf("$");
			if (lastIndex <= VERSION.length()) {
				throw new IllegalArgumentException("invalid hash value" + hashed);
			}
			String lengthStr = hashed.substring(VERSION.length(), lastIndex);
			int length = Integer.parseInt(lengthStr);
			if (hashed.length() <= lastIndex + length + 1) {
				throw new IllegalArgumentException("invalid hash value" + hashed);
			}
			String salt = hashed.substring(lastIndex + 1, lastIndex + length + 1);
			if (hashed.charAt(lastIndex + length + 1) != '.') {
				throw new IllegalArgumentException("invalid hash value" + hashed);
			}
			hash = hashed.substring(lastIndex + length + 2);
			pwdhash = salt + password;
			if (hashed.startsWith("$3b$"))
				pwdhash = Base64.encodeBase64String(DigestUtils.sha512(pwdhash.getBytes(StandardCharsets.UTF_8)));
			else if (hashed.startsWith(VERSION)) {
				pwdhash = Base64.encodeBase64String(AES.encrypt(pwdhash, ASE_SECRET_KEY));
			}

		}
		logger.info("encrypted inputted password is:" + pwdhash);
		return pwdhash.equals(hash);
	}

	// updated by Alex.Yu on 2016.10.24
	/**
	 * 对AES加密的密码进行解密
	 * @param dbPassword
	 * @return md5密码
	 */
	public String decryptAESPassword(String dbPassword) {
		int lastIndex = dbPassword.lastIndexOf("$");
		if (lastIndex <= VERSION.length()) {
			throw new IllegalArgumentException("invalid hash value" + dbPassword);
		}
		String lengthStr = dbPassword.substring(VERSION.length(), lastIndex);
		int length = Integer.parseInt(lengthStr);
		if (dbPassword.length() <= lastIndex + length + 1) {
			throw new IllegalArgumentException("invalid hash value" + dbPassword);
		}
		String salt = dbPassword.substring(lastIndex + 1, lastIndex + length + 1);
		if (dbPassword.charAt(lastIndex + length + 1) != '.') {
			throw new IllegalArgumentException("invalid hash value" + dbPassword);
		}
		String hash = dbPassword.substring(lastIndex + length + 2);
		byte[] passwordArray = Base64.decodeBase64(hash);
		String md5PasswordWithSalt = new String(AES.decrypt(passwordArray, ASE_SECRET_KEY));
		String md5Password = md5PasswordWithSalt.substring(length);
		return md5Password;
	}
	public static void main(String[] args) {
		String password="123456";
		String dbpassword="$4a$15$ujzacixibzrhgsp.cceuQDF6ZNBPh273ny2Z3ih1l+eFtpQcKspo/APHNDMngKcr+RPzmSfV/FvG0MuU";
		PasswordService passwordService=new PasswordService();
		System.out.println(passwordService.hashPassword(password));
		System.out.println(passwordService.hashPassword(password));
//		String dbmd5=passwordService.decryptAESPassword(dbpassword);
//
//		String md5PassWd=MD5Util.MD5Encode(password);
////		String db=passwordService.hashPassword(md5PassWd);
//
//		Assert.isTrue(dbmd5.equals(md5PassWd));

	}
}
