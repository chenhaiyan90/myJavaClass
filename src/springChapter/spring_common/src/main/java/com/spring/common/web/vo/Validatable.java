package com.spring.common.web.vo;

import java.io.Serializable;

import com.spring.common.exception.ApplicationException;

/**
 * 通用的request请求vo的接口
 * @author chenhaiyan
 * @date 2016年3月28日
 */
public interface Validatable extends Serializable {

	/**
	 * 
	 * @author chenhaiyan
	 * @return
	 */
	public boolean validate() throws ApplicationException;
	
}
