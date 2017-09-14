package com.spring.common.web.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.spring.common.exception.ApplicationException;
import com.spring.common.web.vo.ResponseVo;

public class ExceptionFilter implements Filter {
	private static final Logger logger = LoggerFactory.getLogger(ExceptionFilter.class);

	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			
			chain.doFilter(request, response);
			
		} catch (Exception e) {
			
			Throwable cause = e.getCause();
			response.setContentType("application/json;charset=UTF-8");
			ResponseVo<Object> respVo = null;
			PrintWriter pw = response.getWriter();
			
			//TODO 这里只处理ApplicationException 
			if(cause instanceof ApplicationException){
				ApplicationException aep = (ApplicationException) cause;
				respVo = new ResponseVo<Object>();
				respVo.setErrcode(aep.getErrorCode());
				respVo.setErrmsg(aep.getErrorMessage());
				
				//只打印行数
				StackTraceElement stackTraceElement= cause.getStackTrace()[0];// 得到异常棧的首个元素
				logger.warn("ApplicationException File="+stackTraceElement.getFileName() + " Line="+stackTraceElement.getLineNumber() +" Method="+stackTraceElement.getMethodName() + " msg="+aep.getErrorMessage());// 打印文件名
				
			}else{
				logger.error(e.getMessage(), e);//log only when not ApplicationException
				// 返回客户端一个系统错误
				respVo = new ResponseVo<Object>(-1999, e.getMessage());
			}
			pw.write(respVo.toJson());
			pw.flush();
			pw.close();
		}
	}

	public void destroy() {
	}

	
	
}
