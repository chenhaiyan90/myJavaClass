package com.spring.common.util.enums;

import org.apache.commons.lang.StringUtils;

/**
 * @Description:快递员和商户的审核状态
 * @author hezhen
 * @date 2016年6月8日 下午3:22:18
 */
public enum ApprovalStatus {
	 NoVerify,//未认证：0
	 InVerify,//认证中： 1
	 Verified,//已认证： 2
	 Failed;//认证未通过：3

	public static boolean contain(String approvalStatus){
		if(StringUtils.isNotEmpty(approvalStatus)){
			for (ApprovalStatus ut : ApprovalStatus.values()) {
				if(ut.name().equals(approvalStatus)){
					return true;
				}
			}
		}
		return false;
	}
}
