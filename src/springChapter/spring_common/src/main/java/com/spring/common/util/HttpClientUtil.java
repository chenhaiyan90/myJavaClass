package com.spring.common.util;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import com.alibaba.fastjson.TypeReference;

/**
 *
 *
 */
public class HttpClientUtil {

    private PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();

    private HttpClientBuilder clientBuilder;

    private static class Holder {
        static HttpClientUtil instance = new HttpClientUtil();
    }

    private HttpClientUtil() {
        // Increase max total connection to 200
        cm.setMaxTotal(200);
        // Increase default max connection per route to 20
        cm.setDefaultMaxPerRoute(20);
        // Increase max connections for localhost:80 to 50
        HttpHost localhost = new HttpHost("locahost");
        cm.setMaxPerRoute(new HttpRoute(localhost), 50);

        clientBuilder = HttpClients.custom().setConnectionManager(this.cm);
    }

    public static HttpClientUtil getInstance() {
        return Holder.instance;
    }

    /**
     * 用PoolingHttpClientConnectionManager 来做http连接池，提高性能
     *
     * @return
     */
    public synchronized CloseableHttpClient getCloseableHttpClient() {
        return this.clientBuilder.build();
    }

    /**
     * @param url
     * @param cokkie
     * @return
     * @throws Exception
     * @author chenhaiyan
     */
    public String getContentWithCokkie(String url, String cokkie)
            throws Exception {
        return HttpUtil.getContentWithCokkie(url, cokkie,
                this.getCloseableHttpClient());
    }

    /**
     * @param url
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public String getContent(String url) throws Exception {
        return this.getContentWithCokkie(url, null);
    }

    /**
     * @param url
     * @param json
     * @return
     * @throws Exception
     * @author chenhaiyan
     */
    public Serializable post(String url, String json, Serializable t)
            throws Exception {
        return HttpUtil.post(url, json, this.getCloseableHttpClient(), t);
    }


    /**
     * @param url
     * @param json
     * @return
     * @throws Exception
     * @author chenhaiyan
     */
    public String post(String url, String json) throws Exception {
        return HttpUtil.post(url, json, getCloseableHttpClient(), false);
    }
    
    /**
     * @param url
     * @param json
     * @param timeout
     * @return
     * @throws Exception
     */
    public String post(String url, String json, int timeout) throws Exception {
        return HttpUtil.post(url, json, timeout, getCloseableHttpClient(), false);
    }
    
    /**
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public String postParams(String url, Map<String, String> params) throws Exception {
    	return HttpUtil.post(url, params, getCloseableHttpClient());
    }
    
    /**
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public String postParams(String url, Map<String, String> params, int timeout) throws Exception {
    	return HttpUtil.post(url, params, timeout, getCloseableHttpClient());
    }

    /**
     * post content type : application/json
     *
     * @param url
     * @param json
     * @return
     * @throws Exception
     */
    public String postJson(String url, String json) throws Exception {
        return HttpUtil.post(url, json, getCloseableHttpClient(), true);
    }
    
    /**
     * post content type : application/json
     *
     * @param url
     * @param json
     * @return
     * @throws Exception
     */
    public String postJson(String url, String json, int timeout) throws Exception {
        return HttpUtil.post(url, json, timeout, getCloseableHttpClient(), true);
    }

    /**
     * @param url
     * @param json
     * @return
     * @throws Exception
     * @author chenhaiyan
     */
    public <T> T post(String url, String json, TypeReference<T> type)
            throws Exception {
        return HttpUtil.post(url, json, this.getCloseableHttpClient(), type);
    }

    /**
     * @param url
     * @param type
     * @return
     * @throws Exception
     * @Description:
     */
    public <T> T get(String url, TypeReference<T> type)
            throws Exception {
        return HttpUtil.get(url, this.getCloseableHttpClient(), type);
    }
    
    /**
     * @param url
     * @return
     * @throws Exception
     * @Description:
     */
    public String get(String url)
            throws Exception {
        return HttpUtil.get(url, this.getCloseableHttpClient());
    }
    
    /**
     * @param url
     * @param timeout Unit:ms
     * @return
     * @throws Exception
     * @Description:
     */
    public String get(String url, int timeout)
            throws Exception {
        return HttpUtil.get(url, timeout, this.getCloseableHttpClient());
    }
    
    

    /**
     * @param url
     * @param json
     * @param contentType
     * @param type
     * @param <T>
     * @return
     * @throws Exception
     * @author Bek
     */
    public <T> T post(String url, String json, String contentType, TypeReference<T> type)
            throws Exception {
        return HttpUtil.post(url, json, contentType, this.getCloseableHttpClient(), type);
    }

    /***
     * @author Bek
     * @param url
     * @param contentType
     * @param type
     * @return
     * @throws Exception
     */
    public <T> T get(String url, String contentType, TypeReference<T> type)
            throws Exception {
        return HttpUtil.get(url, contentType, this.getCloseableHttpClient(), type);
    }
    
    public byte[] post(String url,byte[] bytes, Map<String, String> headers, int timeout) throws Exception {
    	return HttpUtil.post(url, bytes, headers, timeout, this.getCloseableHttpClient());
    }
}
