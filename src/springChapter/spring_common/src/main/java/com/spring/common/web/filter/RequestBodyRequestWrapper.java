package com.spring.common.web.filter;


import org.apache.commons.io.IOUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @Description： 增加Wrapper让RequestStream可以多次读取
 * Created by Ambitor on 2016/11/2.
 */
public class RequestBodyRequestWrapper extends HttpServletRequestWrapper {

    private final byte[] body;

    public RequestBodyRequestWrapper(HttpServletRequest request)
            throws IOException {
        super(request);
        body = IOUtils.toByteArray(request.getInputStream());
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream bais = new ByteArrayInputStream(body);
        return new ServletInputStream() {

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
            }

            @Override
            public int read() throws IOException {
                return bais.read();
            }
        };
    }
}
