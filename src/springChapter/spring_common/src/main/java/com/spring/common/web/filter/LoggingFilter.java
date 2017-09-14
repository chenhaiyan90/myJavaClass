package com.spring.common.web.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

public class LoggingFilter extends OncePerRequestFilter {

    protected static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

    private static final String REQUEST_PREFIX = "Request: ";
    private static final String RESPONSE_PREFIX = "Response: ";
    public static final String MULTIPART = "multipart/";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
			final FilterChain filterChain) throws ServletException, IOException {
		long tid = Thread.currentThread().getId();
		MDC.put("tid", String.valueOf(tid));
		boolean responseIsbinary = false;
		if (!isMultipart(request) && !isBinaryContent(request.getContentType())) {
			request = new ContentCachingRequestWrapper(request);
		}
		if (isNotBinaryOutput(request) && !isHessianOutput(request)) {
			response = new ContentCachingResponseWrapper(response);
		} else {
		    responseIsbinary = true;
		}
		PerformanceCounter counter = new PerformanceCounter();
		try {
			counter.start();
			filterChain.doFilter(request, response);
			counter.end();
		} finally {
		    if (response.getContentType() != null && response.getContentType().contains("application/octet-stream")) {
		        responseIsbinary = true;
            }
			String requestLog = logRequest(request);
			String responseLog;
			if (responseIsbinary) {
                responseLog = "binary response";
            } else {
                responseLog = logResponse(response);
            }
			logger.info(requestLog + "##" + responseLog + "##" + "Consume:" + counter.spend() + "ms");
			// 清理当前线程里的临时日志变量
			MDC.clear();
		}
	}
    
    private boolean isNotBinaryOutput(HttpServletRequest request) {
        String uri = request.getRequestURI().toLowerCase();
        if (uri.startsWith("/charge_manage/active/selectActive")) {
            return false;
        }
        int lastSlash = uri.lastIndexOf('/');
        while (uri.endsWith("/")) {
            uri = uri.substring(0, lastSlash);
            lastSlash = uri.lastIndexOf('/');
        }
        String method = uri.substring(lastSlash);
        if (method.startsWith("export") || method.startsWith("download") || method.startsWith("/export") || 
                method.startsWith("/download")) {
            return false;
        }
        return true;
    }
    
    private boolean isHessianOutput(HttpServletRequest request) {
        if(request.getContentType() == null){
            return false;
        }
        return request.getContentType().contains("x-application/hessian");
    }

    private boolean isBinaryContent(String contentType) {
        if (contentType == null) {
            return false;
        }
        return contentType.startsWith("image") || contentType.startsWith("video") || contentType.startsWith("audio")
                || contentType.startsWith("application/octet-stream") || contentType.contains("x-application/hessian");
    }

    private boolean isMultipart(HttpServletRequest request) {
        if (!"post".equals(request.getMethod().toLowerCase())) {
            return false;
        }
        String contentType = request.getContentType();
        if (contentType == null) {
            return false;
        }
        if (contentType.toLowerCase().startsWith(MULTIPART)) {
            return true;
        }
        return false;
    }
    
    private String logRequest(HttpServletRequest request) {
        StringBuilder msgBuilder = new StringBuilder();
        msgBuilder.append(REQUEST_PREFIX);
        HttpSession session = request.getSession(false);
        if (session != null) {
            msgBuilder.append("sessionid=").append(session.getId()).append("; ");
        }
        if (request.getMethod() != null) {
            msgBuilder.append("method=").append(request.getMethod()).append("; ");
        }
        if (request.getContentType() != null) {
            msgBuilder.append("content-type=").append(request.getContentType())
                    .append("; ");
        }
        msgBuilder.append("uri=").append(request.getRequestURI());
        if (request.getQueryString() != null) {
            msgBuilder.append('?').append(request.getQueryString());
        }
        msgBuilder.append("; body=").append(getRequestPayload(request));
        return msgBuilder.toString();
    }
    
    private String logResponse(HttpServletResponse response) {
        StringBuilder msgBuilder = new StringBuilder(RESPONSE_PREFIX);
        msgBuilder.append("content-type=")
                    .append(response.getContentType())
                    .append("; ")
                    .append("body=")
                    .append(getResponsePayload(response));
        return msgBuilder.toString();
    }

	private String getRequestPayload(HttpServletRequest request) {
	    String payload = null;
	    ContentCachingRequestWrapper wrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
	    if (wrapper != null) {
	        byte[] buf = wrapper.getContentAsByteArray();
	        if (buf.length > 0) {
	            try {
                    payload = new String(buf, wrapper.getCharacterEncoding());
                } catch (UnsupportedEncodingException e) {
                    logger.error("failed to get request payload", e);
                }
	        }
	    }
	    return payload;
	}
	
	private String getResponsePayload(HttpServletResponse response) {
        String payload = null;
        ContentCachingResponseWrapper wrapper = WebUtils.getNativeResponse(response, 
                ContentCachingResponseWrapper.class);
        if (wrapper != null) {
            byte[] buff = wrapper.getContentAsByteArray();
            if (buff.length > 0) {
                try {
                    payload = new String(buff, response.getCharacterEncoding());
                    wrapper.copyBodyToResponse();
                } catch (IOException e) {
                    logger.warn("failed to get response payload", e);
                }
            }
        }
        return payload;
    }
	
}
