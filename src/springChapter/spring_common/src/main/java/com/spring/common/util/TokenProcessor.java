package com.spring.common.util;

public class TokenProcessor {

	/**
	 * 
	 * @author chenhaiyan
	 * @return
	 */
	public static String generateTokeCode() {
		StringBuffer sb = new StringBuffer();
		//13位数字
		String str = String.valueOf(System.currentTimeMillis());
		sb.append(MD5Util.randomStr(12));
		for (int i = 0, j = str.length(); i < j; i++) {
			sb.append(MD5Util.randomStr(2));
			sb.append(str.charAt(i));
		}
		sb.append(MD5Util.randomStr(13));
		return sb.toString();
	}
}
