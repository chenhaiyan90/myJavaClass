package com.spring.common.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

public class HttpUtil {

    public final static String ENCODING = "UTF-8";

    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    /**
     * @param url
     * @return
     * @throws MalformedURLException
     * @author chenhaiyan
     */
    public static String getHostByLink(String url) throws MalformedURLException {
        URL aURL = new URL(url);
        return aURL.getHost();
    }

    /**
     * @param url
     * @return
     * @throws Exception
     * @author chenhaiyan
     */
    public String getRealUrlWhenRedirect(String url) throws Exception {
        URL u = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) u.openConnection();
        conn.setInstanceFollowRedirects(false);
        int statusCode = conn.getResponseCode();
        if ((statusCode == HttpStatus.SC_MOVED_TEMPORARILY)
                || (statusCode == HttpStatus.SC_MOVED_PERMANENTLY)
                || (statusCode == HttpStatus.SC_SEE_OTHER)
                || (statusCode == HttpStatus.SC_TEMPORARY_REDIRECT)) {
            url = conn.getHeaderField("Location");
        }
        conn.disconnect();
        return url;
    }

    public static HttpGet initGetFunction(URI uri) {
        HttpGet httpget = new HttpGet(uri);
        httpget.setHeader("Accept",
                "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        httpget.setHeader(
                "User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1612.2 ");
        // httpget.setHeader("Accept-Encoding", "gzip, deflate");
        httpget.setHeader("Host", uri.getHost());
        httpget.setHeader("Connection", "Keep-Alive");
        httpget.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
        httpget.setHeader("Cache-Control", "max-age=0");
        return httpget;
    }

    /**
     * @param url
     * @param cokkie
     * @param httpClient
     * @return
     * @throws Exception
     * @author chenhaiyan
     */
    public static String getContentWithCokkie(String url, String cokkie,
                                              CloseableHttpClient httpClient) throws Exception {
        String htmlContent = "";
        URL urlobj = new URL(url);
        URI uri = null;
        if (urlobj.getPort() != -1) {
            uri = new URI(urlobj.getProtocol(), "", urlobj.getHost(), urlobj.getPort(),
                    urlobj.getPath(), urlobj.getQuery(), null);
        } else {
            uri = new URI(urlobj.getProtocol(), urlobj.getHost(),
                    urlobj.getPath(), urlobj.getQuery(), null);
        }
        HttpGet httpget = HttpUtil.initGetFunction(uri);

        if (!StringUtils.isEmpty(cokkie)) {
            httpget.setHeader("Cookie", cokkie);
        }

        HttpResponse response = null;
        try {
            response = httpClient.execute(httpget);
            response.setHeader("Transfer-Encoding", ENCODING);
            // response.setHeader("Transfer-Encoding", "chunked");
        } catch (IllegalStateException ex) {
            throw ex;
        }

        HttpEntity entity = response.getEntity();
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != HttpStatus.SC_OK) {
            httpget.abort();
            EntityUtils.consume(entity);
            return htmlContent;
        }

        if (entity != null) {
            byte[] bytes = EntityUtils.toByteArray(entity);
            htmlContent = new String(bytes, ENCODING);
        }
        httpget.abort();
        EntityUtils.consume(entity);
        return htmlContent;
    }

    /**
     * @param url
     * @param json
     * @param httpClient
     * @return
     * @throws Exception
     * @author chenhaiyan
     */
    public static Serializable post(String url, String json,
                                    CloseableHttpClient httpClient, Serializable t) throws Exception {
        String strResult = "";
        HttpPost post = new HttpPost(url);
        HttpResponse response = null;
        try {
            StringEntity entity = new StringEntity(json, ENCODING);
            post.setEntity(entity);
            response = httpClient.execute(post);
            response.setHeader("Transfer-Encoding", ENCODING);

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                return null;
            }
            strResult = EntityUtils.toString(response.getEntity(), "utf-8");

            return JSON.parseObject(strResult, t.getClass());

        } finally {
            post.abort();
        }

    }

    /**
     * @param url
     * @param json
     * @param httpClient
     * @return
     * @throws Exception
     * @author chenhaiyan
     */
    public static String post(String url, String json,
			CloseableHttpClient httpClient, boolean isContentTypeJson) throws Exception {
		String strResult = "";
		HttpPost post = new HttpPost(url);
		HttpResponse response = null;
		int statusCode = 0;
		long time = 0;
		try {
		    if (StringUtils.isNotEmpty(json)) {
                StringEntity entity = new StringEntity(json, ENCODING);
                post.setEntity(entity);
    			if (isContentTypeJson) {
    				entity.setContentType(ContentType.APPLICATION_JSON.getMimeType());
    			}
    			post.setEntity(entity);
		    }
			long start = System.currentTimeMillis();
			response = httpClient.execute(post);
			time = System.currentTimeMillis() - start;
			statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				return null;
			}
			strResult = EntityUtils.toString(response.getEntity(), "utf-8");
			return strResult;
		} finally {
			logger.info("|post|{}|status:{}|{}ms|body:{}|response:{} ", url, statusCode, time, json, strResult);
			post.abort();
		}
	}

    
    /**
     * @param url
     * @param json
     * @param httpClient
     * @param timeout Unit:ms
     * @return
     * @throws Exception
     */
    public static String post(String url, String json, int timeout,
			CloseableHttpClient httpClient, boolean isContentTypeJson) throws Exception {
		String strResult = "";
		HttpPost post = new HttpPost(url);
		post.setConfig(getRequestConfig(timeout));
		HttpResponse response = null;
		int statusCode = 0;
		long time = 0;
		long start = 0;
		try {
			if (StringUtils.isNotEmpty(json)) {
				StringEntity entity = new StringEntity(json, ENCODING);
				post.setEntity(entity);
				if (isContentTypeJson) {
					entity.setContentType(ContentType.APPLICATION_JSON.getMimeType());
				}
				post.setEntity(entity);
			}
			start = System.currentTimeMillis();
			response = httpClient.execute(post);
			time = System.currentTimeMillis() - start;
			statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				return null;
			}
			strResult = EntityUtils.toString(response.getEntity(), "utf-8");
			return strResult;
		} catch (Exception e) {
			time = System.currentTimeMillis() - start;
			strResult = e.getMessage();
			throw e;
		} finally {
			logger.info("|post|{}|status:{}|{}ms|body:{}|response:{} ", url, statusCode, time, json, strResult);
			post.abort();
		}
	}
    

    /**
     * @param url
     * @param json
     * @param httpClient
     * @return
     * @throws Exception
     * @author chenhaiyan
     */
    public static <T> T post(String url, String json,
                             CloseableHttpClient httpClient, TypeReference<T> type)
            throws Exception {
        String strResult = "";
        HttpPost post = new HttpPost(url);
        HttpResponse response = null;
        try {
            if (!StringUtils.isEmpty(json)) {
                StringEntity entity = new StringEntity(json, ENCODING);
                post.setEntity(entity);
            }
            logger.info("发送请求至 ：url={}, requestBody={}", url, json);
            response = httpClient.execute(post);
            response.setHeader("Transfer-Encoding", ENCODING);

            int statusCode = response.getStatusLine().getStatusCode();
            logger.info("statusCode={}", statusCode);
            if (statusCode != HttpStatus.SC_OK) {
                return null;
            }
            strResult = EntityUtils.toString(response.getEntity(), "utf-8");

            logger.info("返回结果：{}", strResult);
            return JSON.parseObject(strResult, type);

        } catch (ClientProtocolException e) {
            logger.error("Http Post ClientProtocolException", e);
            throw new Exception(e);
        } catch (IOException e) {
            logger.error("Http Post IOException", e);
            throw new Exception(e);
        } finally {
            post.abort();
        }

    }
    
    /**
     * @param url
     * @param httpClient
     * @return
     * @throws Exception
     * @author chenhaiyan
     */
    public static String get(String url,
                            CloseableHttpClient httpClient)
            throws Exception {
        logger.info("请求URL：" + url);
        String strResult = "";
        HttpGet get = new HttpGet(url);
        get.setConfig(getRequestConfig());

        HttpResponse response = null;
        try {
            response = httpClient.execute(get);

            response.setHeader("Transfer-Encoding", ENCODING);

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                return null;
            }
            byte[] bytes = EntityUtils.toByteArray(response.getEntity());
            strResult = new String(bytes, Consts.UTF_8.toString());

            logger.info("返回结果：" + strResult);

            return strResult;

        } catch (ClientProtocolException e) {
            logger.error("Http Post ClientProtocolException", e);
            throw new Exception(e);
        } catch (IOException e) {
            logger.error("Http Post IOException", e);
            throw new Exception(e);
        } finally {
            get.abort();
        }

    }
    
    /**
     * @param url
     * @param httpClient
     * @param timeout Unit:ms
     * @return
     * @throws Exception
     */
    public static String get(String url, int timeout,
                            CloseableHttpClient httpClient)
            throws Exception {
        logger.info("请求URL：" + url);
        String strResult = "";
        HttpGet get = new HttpGet(url);
        get.setConfig(getRequestConfig(timeout));

        HttpResponse response = null;
        try {
            response = httpClient.execute(get);

            response.setHeader("Transfer-Encoding", ENCODING);

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                return null;
            }
            byte[] bytes = EntityUtils.toByteArray(response.getEntity());
            strResult = new String(bytes, Consts.UTF_8.toString());

            logger.info("返回结果：" + strResult);

            return strResult;

        } catch (ClientProtocolException e) {
            logger.error("Http Post ClientProtocolException", e);
            throw new Exception(e);
        } catch (IOException e) {
            logger.error("Http Post IOException", e);
            throw new Exception(e);
        } finally {
            get.abort();
        }

    }

    /**
     * @param url
     * @param httpClient
     * @return
     * @throws Exception
     * @author chenhaiyan
     */
    public static <T> T get(String url,
                            CloseableHttpClient httpClient, TypeReference<T> type)
            throws Exception {
        logger.info("请求URL：" + url);
        String strResult = "";
        HttpGet get = new HttpGet(url);
        get.setConfig(getRequestConfig());

        HttpResponse response = null;
        try {
            response = httpClient.execute(get);

            response.setHeader("Transfer-Encoding", ENCODING);

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                return null;
            }
            byte[] bytes = EntityUtils.toByteArray(response.getEntity());
            strResult = new String(bytes, Consts.UTF_8.toString());

            logger.info("返回结果：" + strResult);

            return JSON.parseObject(strResult, type.getType());

        } catch (ClientProtocolException e) {
            logger.error("Http Post ClientProtocolException", e);
            throw new Exception(e);
        } catch (IOException e) {
            logger.error("Http Post IOException", e);
            throw new Exception(e);
        } finally {
            get.abort();
        }

    }


    /**
     * 获取RequestConfig
     *
     * @return
     * @Description:
     */
    private static RequestConfig getRequestConfig() {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(10000).setConnectTimeout(10000)
                .setSocketTimeout(10000).build();
        return requestConfig;
    }
    
    /**
     * 获取RequestConfig
     * 自定义超时
     * @param timeout Unit: ms
     * @return
     * @Description:
     */
    private static RequestConfig getRequestConfig(int timeout) {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(timeout).setConnectTimeout(timeout)
                .setSocketTimeout(timeout).build();
        return requestConfig;
    }

    /**
     * Bek
     *
     * @param url
     * @param json
     * @param httpClient
     * @param contentType
     * @param type
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T post(String url, String json, String contentType,
                             CloseableHttpClient httpClient, TypeReference<T> type)
            throws Exception {
        String strResult = null;
        HttpPost post = new HttpPost(url);
        post.addHeader("Content-Type", contentType);
        HttpResponse response = null;
        try {
            StringEntity entity = new StringEntity(json, ENCODING);
            post.setEntity(entity);
            logger.info("发送请求至 ：url={}, requestBody={}", url, json);
            response = httpClient.execute(post);

            int statusCode = response.getStatusLine().getStatusCode();
            logger.info("statusCode={}", statusCode);
            if (statusCode != HttpStatus.SC_OK) {
                return null;
            }
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(responseEntity.getContent()));
                StringBuffer textBuffer = new StringBuffer();
                String text = null;
                while ((text = bufferedReader.readLine()) != null) {
                    textBuffer.append(text);
                }
                strResult = textBuffer.toString();
            }
            logger.info("返回结果：{}", strResult);
            return JSON.parseObject(strResult, type);

        } catch (ClientProtocolException e) {
            logger.error("Http Post ClientProtocolException", e);
            throw new Exception(e);
        } catch (IOException e) {
            logger.error("Http Post IOException", e);
            throw new Exception(e);
        } finally {
            post.abort();
        }

    }


    public static <T> T get(String url, String contentType,
                            CloseableHttpClient httpClient, TypeReference<T> type)
            throws Exception {
        logger.info("请求URL：" + url);
        String strResult = "";
        HttpGet get = new HttpGet(url);
        get.addHeader("Content-Type", contentType);
        get.setConfig(getRequestConfig());

        HttpResponse response = null;
        try {
            response = httpClient.execute(get);
            int statusCode = response.getStatusLine().getStatusCode();
            logger.info("statusCode" + statusCode);
            if (statusCode != HttpStatus.SC_OK) {
                return null;
            }
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(responseEntity.getContent()));
                StringBuffer textBuffer = new StringBuffer();
                String text = null;
                while ((text = bufferedReader.readLine()) != null) {
                    textBuffer.append(text);
                }
                strResult = textBuffer.toString();
            }

            logger.info("返回结果：" + strResult);

            return JSON.parseObject(strResult, type);

        } catch (ClientProtocolException e) {
            logger.error("Http get ClientProtocolException", e);
            throw new Exception(e);
        } catch (IOException e) {
            logger.error("Http get IOException", e);
            throw new Exception(e);
        } finally {
            get.abort();
        }
    }
    
    public static String post(String url, Map<String, String> params, CloseableHttpClient httpClient) throws Exception {
    	HttpPost post = new HttpPost(url);
    	HttpResponse response = null;
    	String strResult = null;
    	List<NameValuePair> paramList = new ArrayList<NameValuePair>();
    	if(null != params && params.size() > 0) {
    		for(Map.Entry<String, String> entry : params.entrySet()) {
    			paramList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
    		}
    	}
    	try {
			HttpEntity entity = new UrlEncodedFormEntity(paramList);
			post.setEntity(entity);
			response = httpClient.execute(post);
			 int statusCode = response.getStatusLine().getStatusCode();
	            logger.info("statusCode={}", statusCode);
	            if (statusCode != HttpStatus.SC_OK) {
	                return null;
	            }
	            strResult = EntityUtils.toString(response.getEntity(), "utf-8");
	            logger.info("返回结果：{}", strResult);
	            return strResult;
		} catch (ClientProtocolException e) {
			logger.error("Http post ClientProtocolException", e);
            throw new Exception(e);
		} catch (IOException ie) {
			logger.error("Http Post IOException", ie);
            throw new Exception(ie);
		} finally {
			post.abort();
		}
    }
    
    public static String post(String url, Map<String, String> params, int timeout, CloseableHttpClient httpClient) throws Exception {
    	HttpPost post = new HttpPost(url);
    	post.setConfig(getRequestConfig(timeout));
    	HttpResponse response = null;
    	String strResult = null;
    	List<NameValuePair> paramList = new ArrayList<NameValuePair>();
    	if(null != params && params.size() > 0) {
    		for(Map.Entry<String, String> entry : params.entrySet()) {
    			paramList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
    		}
    	}
    	try {
			HttpEntity entity = new UrlEncodedFormEntity(paramList);
			post.setEntity(entity);
			response = httpClient.execute(post);
			 int statusCode = response.getStatusLine().getStatusCode();
	            logger.info("statusCode={}", statusCode);
	            if (statusCode != HttpStatus.SC_OK) {
	                return null;
	            }
	            strResult = EntityUtils.toString(response.getEntity(), "utf-8");
	            logger.info("返回结果：{}", strResult);
	            return strResult;
		} catch (ClientProtocolException e) {
			logger.error("Http post ClientProtocolException", e);
            throw new Exception(e);
		} catch (IOException ie) {
			logger.error("Http Post IOException", ie);
            throw new Exception(ie);
		} finally {
			post.abort();
		}
    } 
    
    public static byte[] post(String url, byte[] bytes, Map<String, String> headers,  int timeout, CloseableHttpClient httpClient) throws Exception {
    	HttpPost post = new HttpPost(url);
    	post.setConfig(getRequestConfig(timeout));
    	HttpResponse response = null;
    	post.setEntity(new ByteArrayEntity(bytes));
    	for(Map.Entry<String, String> entry : headers.entrySet()) {
    		post.setHeader(entry.getKey(), entry.getValue());
    	}
    	try {
    		response = httpClient.execute(post);
    		logger.info("url:{}|response:{}", url, response);
    		int statusCode = response.getStatusLine().getStatusCode();
            logger.info("statusCode={}", statusCode);
            if (statusCode != HttpStatus.SC_OK) {
                return null;
            }
    		Header[] respHeaders = response.getHeaders("result");
    		for(Header hd : respHeaders) {
    			if("result".equalsIgnoreCase(hd.getName())) {
    				if("SUCCESS".equals(hd.getValue())) {
    					HttpEntity entity = response.getEntity();
    					InputStream in = entity.getContent();
    					ByteArrayOutputStream out = new ByteArrayOutputStream();
    					byte[] tmp = new byte[4096];
    					int n = 0;
    					while(-1 != (n = in.read(tmp))) {
    						out.write(tmp, 0, n);
    					}
    					return out.toByteArray();
    				}
    			}
    		}
		} catch (ClientProtocolException e) {
			logger.error("Http post ClientProtocolException", e);
            throw new Exception(e);
		} catch (Exception ie) {
			logger.error("HTTP post byte IOException", ie);
			throw new Exception(ie);
		} finally {
			post.abort();
		}
		return null;
    }

}
