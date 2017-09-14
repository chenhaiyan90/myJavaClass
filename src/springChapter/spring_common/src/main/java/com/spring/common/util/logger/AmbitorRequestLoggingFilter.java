package com.spring.common.util.logger;

import org.springframework.web.filter.AbstractRequestLoggingFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description： 日志filter
 * Created by Ambitor on 2016/11/7.
 */
public class AmbitorRequestLoggingFilter extends AbstractRequestLoggingFilter {

    public AmbitorRequestLoggingFilter() {
        setIncludePayload(true);
        setIncludeQueryString(true);
        setMaxPayloadLength(5000);
        setIncludeClientInfo(true);
    }

    @Override
    protected void beforeRequest(HttpServletRequest request, String message) {
        startTime.set(System.currentTimeMillis());
        logger.info("请求开始-----" + message.replaceAll("\r|\n| ", "") + "------");
    }

    @Override
    protected void afterRequest(HttpServletRequest request, String message) {
        logger.info("请求结束----" + message.replaceAll("\r|\n| ", "") + "------耗时:" + (System.currentTimeMillis() - startTime.get()));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (isNotBinaryOutput(request) && !isAsyncDispatch(request) && !(response instanceof ContentCachingResponseWrapper)) {
            response = new ContentCachingResponseWrapper(response);
        }

        super.doFilterInternal(request, response, filterChain);

        if (!isBinaryContent(response.getContentType())) {
            StringBuilder msgBuilder = new StringBuilder("返回信息----");
            msgBuilder.append("content-type=").append(response.getContentType()).append("; ").append("body=")
                    .append(getResponsePayload(response)).append("------");
            logger.info(msgBuilder.toString());
        }
    }

    private String getResponsePayload(HttpServletResponse response) {
        String payload = null;
        if (!(response instanceof ContentCachingResponseWrapper)) return payload;
        ContentCachingResponseWrapper wrapper = (ContentCachingResponseWrapper) response;
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

    private boolean isBinaryContent(String contentType) {
        if (contentType == null) return false;
        return contentType.contains("image") || contentType.contains("video") || contentType.contains("audio")
                || contentType.contains("application/octet-stream") || contentType.contains("multipart/form-data");
    }

    private boolean isNotBinaryOutput(HttpServletRequest request) {
        String uri = request.getRequestURI().toLowerCase();
        if (uri.startsWith("/charge_manage/active/selectActive")) {
            return false;
        }
        String method = uri.substring(uri.lastIndexOf('/'));
        if (method.contains("export") || method.contains("download")) {
            return false;
        }
        return true;
    }

    private ThreadLocal<Long> startTime = new ThreadLocal<>();

}
