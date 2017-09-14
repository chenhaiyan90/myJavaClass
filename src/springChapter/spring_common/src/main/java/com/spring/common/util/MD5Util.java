package com.spring.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.UUID;

/** MD5 */
public class MD5Util {

	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	/**
	 * 转换字节数组为16进制字串
	 * 
	 * @param b
	 *            字节数组
	 * @return 16进制字串
	 */

	public static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	public static String getNonceStr() {
		Random random = new Random();
		return MD5Util.MD5Encode(String.valueOf(random.nextInt(10000)), "UTF-8");
	}
	
	
	public static String MD5Encode(String origin) {
		return MD5Encode(origin,"");
	}

	public static String MD5Encode(String origin, String charsetname) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			if (charsetname == null || "".equals(charsetname))
				resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
			else
				resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
		} catch (Exception exception) {
		}
		return resultString;
	}
	
	private static final char[] CODES = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
			'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
			'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
			'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
			'x', 'y', 'z' };

	private static final char[] NUMBER_CODES = { '0', '1', '2', '3', '4', '5', '6',
		'7', '8', '9'};
	/**
	 * 
	 * @param len
	 * @return
	 */
	public static String randomStr(int len) {
		if (len < 3) {
			return randomStrLessThanThree(len);
		}
		// 数组，用于存放随机字符
		char[] chArr = new char[len];
		// 为了保证必须包含数字、大小写字母
		chArr[0] = (char) ('0' + StdRandom.uniform(0, 10));
		chArr[1] = (char) ('A' + StdRandom.uniform(0, 26));
		chArr[2] = (char) ('a' + StdRandom.uniform(0, 26));

		// charArr[3..len-1]随机生成codes中的字符
		for (int i = 3; i < len; i++) {
			chArr[i] = CODES[StdRandom.uniform(0, CODES.length)];
		}
		// 将数组chArr随机排序
		for (int i = 0; i < len; i++) {
			int r = i + StdRandom.uniform(len - i);
			char temp = chArr[i];
			chArr[i] = chArr[r];
			chArr[r] = temp;
		}

		return new String(chArr);
	}
	
	/**
	 * 
	 * @author chenhaiyan
	 * @param len
	 * @return
	 */
	public static String randomNumberStr(int len) {
		StringBuffer sb = new StringBuffer(len);
		for(int i=0;i<len;i++){
			sb.append(NUMBER_CODES[StdRandom.uniform(0, NUMBER_CODES.length)]);
		}
		return sb.toString();
	}

	/**
	 * 
	 * @author chenhaiyan
	 * @param length
	 * @return
	 */
	private static String randomStrLessThanThree(int length) {
		StringBuffer sb = new StringBuffer(10);
		for (int i = 0; i < length; i++) {
			sb.append(CODES[StdRandom.uniform(0, CODES.length)]);
		}
		return sb.toString();
	}

	/**
	 * 
	 * @return
	 */
	public static String buildRandomSalt() {
		return randomStr(12);
	}

	/**
	 * 
	 * @author chenhaiyan
	 * @return
	 */
	public static String uuid() {
		return MD5Encode(UUID.randomUUID().toString());
	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		String pas = "123";
		String md5 = MD5Util.MD5Encode(pas);
		String salt = "r1nAP6fouP1W";// buildRandomSalt();
		String saltMd5 = MD5Util.MD5Encode(md5 + salt);
		System.out.println(md5);
		System.out.println(salt);
		System.out.println(saltMd5);
		for (int i = 0; i < 20; i++) {
			System.out.println(uuid().hashCode());
			//System.out.println(randomStr(12));
			//System.out.println(randomStr(2));
		}
	}

	public static String getFileMD5String(File file) throws IOException {
	    InputStream fis;
	    fis = new FileInputStream(file);
	    byte[] buffer = new byte[1024];
	    int numRead = 0;
	    MessageDigest messagedigest = null;
		try {
			messagedigest = MessageDigest.getInstance("MD5");
		}
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	    while ((numRead = fis.read(buffer)) > 0) {
	        messagedigest.update(buffer, 0, numRead);
	    }
	    fis.close();
	    return byteArrayToHexString(messagedigest.digest());
	}

	public static String getFileMD5String(InputStream in) throws IOException {
	    byte[] buffer = new byte[1024];
	    int numRead = 0;
	    MessageDigest messagedigest = null;
		try {
			messagedigest = MessageDigest.getInstance("MD5");
		}
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	    while ((numRead = in.read(buffer)) > 0) {
	        messagedigest.update(buffer, 0, numRead);
	    }
	    return byteArrayToHexString(messagedigest.digest());
	}

}