package com.spring.common.util.http;

import java.io.UnsupportedEncodingException;

/**
 * @desc: 封装HttpClient返回数据
 * @User: ambitor_luo
 * @Date: 2015/7/6
 */
public class ResponseMessage {
    /**
     * 返回头中的encoding设置，一般为null
     */
    private String encoding;

    /**
     * 可能是String，或者是文件流等
     */
    private byte[] contentBytes;

    /**
     * Http返回的状态码
     */
    private int statusCode;

    /**
     * 例：text/html
     */
    private String contentType;
    
    /**
     * 例：text/html;charset=utf-8
     */
    private String contentTypeString;

    //------------------------------------- Get Method

    public String getEncoding() {
        return encoding;
    }

    public String getContentType() {
        return this.contentType;
    }

    public String getContentTypeString() {
        return this.contentTypeString;
    }

    public String getContent() {
        return this.getContent("UTF-8");
    }

    public String getContent(String encoding) {
        if (encoding == null) {
            return new String(contentBytes);
        }
        try {
            return new String(contentBytes, encoding);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("不支持的编码格式:" + encoding, e);
        }
    }

    public byte[] getContentBytes() {
        return contentBytes;
    }

    public int getStatusCode() {
        return statusCode;
    }

    //----------------------------------- Set Method
    protected void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    protected void setContentType(String contentType) {
        this.contentType = contentType;
    }

    protected void setContentTypeString(String contenttypeString) {
        this.contentTypeString = contenttypeString;
    }

    protected void setContentBytes(byte[] contentBytes) {
        this.contentBytes = contentBytes;
    }

    protected void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}