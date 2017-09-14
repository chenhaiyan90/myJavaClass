package com.spring.common.util.enums;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @Description:用户类型型枚举类
 * @author hezhen
 * @date 2016年5月16日 下午2:54:44
 */
public enum UserType {
	Customer,//C端用户
	Courier,//快递员 
	Merchant ,//B端商户
	EZ_Admin;//e栈管理员
 
	/**
	 * @Description: 判断该用户类型是否存在
	 * @param userType
	 * @return   true：包含该用户类型，false，不包含该用户类型
	 */
	public static boolean contain(String userType){
		if(StringUtils.isNotEmpty(userType)){ 
			for (UserType ut : UserType.values()) {
			     if(ut.name().equals(userType)){
			    	 return true;
			     }
			}
		}
		return false;
	}
}
