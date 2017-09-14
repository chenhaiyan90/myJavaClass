package com.spring.common.exception;

import java.text.MessageFormat;

import com.alibaba.fastjson.JSONObject;

/**
 * 提供的服务异常，业务不能正常进行
 * @Description:
 * @author hezhen
 * @date 2017年4月26日 上午11:49:56
 */
public class CeltServiceException extends RuntimeException{
	private static final long serialVersionUID = 1823411897154660702L;
	private Integer errorCode;
	private String errorMessage;
	
	/**
	 * 
	 * @param cause
	 */
	public CeltServiceException(Throwable cause) {
	        super(cause);
	}
	
	/**
	 * 
	 * @param message
	 */
	public CeltServiceException(String message) {
        super(message);
    }
	
	/**
	 * 
	 * @param errorCode
	 */
	public CeltServiceException(Integer errorCode) {
		this.errorCode = errorCode;
		this.errorMessage = ExceptionMessage.getErrorMessage(errorCode);
	}
 
	/**
	 * 
	 * @param errorMessage
	 * @param args
	 */
	public CeltServiceException(String errorMessage,Object... args) {  
		
		this.errorMessage = MessageFormat.format(errorMessage, args);
		 
	}
	 
	

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * 将异常转成JSON格式
	 * 
	 * @return JSON格式字符串
	 */
	public String toJson() {
		JSONObject object = new JSONObject();
		object.put("code", errorCode);
		object.put("msg", errorMessage);
		return object.toJSONString();
	}
}
