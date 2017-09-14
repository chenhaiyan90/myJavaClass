package com.spring.common.util.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.spring.common.exception.BusinessException;
import com.spring.common.util.JacksonUtils;

import org.apache.http.HttpException;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.FileBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @desc: 封装HttpClient来对外提供简化的HTTP请求
 * @User: ambitor_luo
 * @Date: 2015/7/6
 */
public class Https {

    private static Integer socketTimeout = 5000;
    private static Integer connectTimeout = 5000;
    private static Integer connectionRequestTimeout = 5000;
    public static final String POST_JSON = "POST/JSON";
    public static final String GET = "GET";
    public static final String POST = "POST";

    /**
     * 封装Https的请求，将JSON解析，返还需要的对象
     * @param url
     * @param type
     * @return 可能返还null
     * @Description:
     */
    public static <T> T get(String url, TypeReference<T> type) {
        ResponseMessage message = Https.get(url);
        if (message != null) {
            String strResult = message.getContent();
            return JSON.parseObject(strResult, type);
        }
        return null;
    }


    /**
     * 封装Https的请求，将JSON解析，返还需要的对象
     * @param url
     * @param json
     * @param type
     * @return 可能返还null
     * @Description:
     */
    public static <T> T post(String url, String json, TypeReference<T> type) {
        ResponseMessage message = Https.postJson(url, json);
        if (message != null) {
            String strResult = message.getContent();
            return JSON.parseObject(strResult, type);
        }
        return null;
    }

    /**
     * 封装Https的请求，将JSON解析，返还需要的对象
     * @param url
     * @param paramsMap
     * @param type
     * @return
     * @Description:
     */
    public static <T> T post(String url, Map<String, Object> paramsMap, TypeReference<T> type) {
        ResponseMessage message = Https.post(url, paramsMap);
        if (message != null) {
            String strResult = message.getContent();
            return JSON.parseObject(strResult, type);
        }
        return null;
    }


    /**
     * 封装Https的请求，将JSON解析，返还需要的对象
     * @param url
     * @param json
     * @param type
     * @param socketTimeout
     * @param connectTimeout
     * @param connectionRequestTimeout
     * @return
     * @Description:
     */
    public static <T> T post(String url, String json, TypeReference<T> type, Integer socketTimeout,
                             Integer connectTimeout, Integer connectionRequestTimeout) {
        ResponseMessage message = postJson(url, json, socketTimeout, connectTimeout, connectionRequestTimeout);
        if (message != null) {
            String strResult = message.getContent();
            return JSON.parseObject(strResult, type);
        }
        return null;
    }


    /**
     * 使用Get方式 根据URL地址，获取ResponseContent对象
     * @param url 完整的URL地址
     * @return ResponseMessage 如果发生异常则返回null，否则返回ResponseContent对象
     */
    public static ResponseMessage get(String url) {
        try {
            HttpClientWrapper hw = new HttpClientWrapper(connectionRequestTimeout, connectTimeout, socketTimeout);
            logger.info("GET请求发送:" + url);
            ResponseMessage message = hw.getResponse(url);
            if (message != null && logger.isInfoEnabled()) logger.info("GET返回结果：{}", message.getContent());
            return message;
        } catch (Exception e) {
            throw new RuntimeException("GET请求发送失败:" + url, e);
        }
    }

    /**
     * 使用Get方式 根据URL地址，获取ResponseContent对象
     * @param url 完整的URL地址
     * @return ResponseMessage 如果发生异常则返回null，否则返回ResponseContent对象
     */
    public static ResponseMessage get(String url, Integer socketTimeout1,
                                      Integer connectTimeout1, Integer connectionRequestTimeout1) {
        socketTimeout1 = socketTimeout1 == null ? socketTimeout : socketTimeout1;
        connectTimeout1 = connectTimeout1 == null ? connectTimeout : connectTimeout1;
        connectionRequestTimeout1 = connectionRequestTimeout1 == null ? connectionRequestTimeout : connectionRequestTimeout1;
        HttpClientWrapper hw = new HttpClientWrapper(connectionRequestTimeout1, connectTimeout1, socketTimeout1);
        try {
            logger.info("GET请求发送:" + url);
            ResponseMessage message = hw.getResponse(url);
            if (message != null && logger.isInfoEnabled()) logger.info("GET返回结果：{}", message.getContent());
            return message;
        } catch (Exception e) {
            throw new RuntimeException("GET请求发送失败:" + url, e);
        }
    }

    /**
     * 使用Get方式 根据URL地址，获取ResponseContent对象
     * @param url       请求URL
     * @param paramsMap 参数和值
     * @return
     */
    public static ResponseMessage get(String url, Map<String, Object> paramsMap) {
        if (paramsMap != null) {
            if (!paramsMap.isEmpty()) url = url.endsWith("?") ? url : (url + "?");
            for (Map.Entry<String, Object> entry : paramsMap.entrySet()) {
                url += entry.getKey() + "=" + String.valueOf(entry.getValue()) + "&";
            }
            url = url.endsWith("&") ? url.substring(0, url.length() - 1) : url;
        }
        return get(url);
    }

    /**
     * 使用Get方式 根据URL地址，获取ResponseContent对象
     * @param url       请求URL
     * @param paramsMap 参数和值
     * @return
     */
    public static ResponseMessage get(String url, Map<String, Object> paramsMap, Integer socketTimeout1,
                                      Integer connectTimeout1, Integer connectionRequestTimeout1) {
        if (paramsMap != null) {
            if (!paramsMap.isEmpty()) url = url.endsWith("?") ? url : (url + "?");
            for (Map.Entry<String, Object> entry : paramsMap.entrySet()) {
                url += entry.getKey() + "=" + String.valueOf(entry.getValue()) + "&";
            }
            url = url.endsWith("&") ? url.substring(0, url.length() - 1) : url;
        }
        return get(url, socketTimeout1, connectTimeout1, connectionRequestTimeout1);
    }

    /**
     * 使用Get方式 根据URL地址，获取ResponseContent对象
     * @param url         完整的URL地址
     * @param urlEncoding 编码，可以为null
     * @return ResponseMessage 如果发生异常则返回null，否则返回ResponseContent对象
     */
    public static ResponseMessage get(String url, String urlEncoding) {
        HttpClientWrapper hw = new HttpClientWrapper(connectionRequestTimeout, connectTimeout, socketTimeout);
        try {
            logger.info("GET请求发送:" + url);
            ResponseMessage message = hw.getResponse(url, urlEncoding);
            if (message != null && logger.isInfoEnabled()) logger.info("GET返回结果：{}", message.getContent());
            return message;
        } catch (Exception e) {
            throw new RuntimeException("GET请求发送失败:" + url, e);
        }

    }

    /**
     * 将参数拼装在url中，进行post请求。
     * @param url
     * @return
     */
    public static ResponseMessage post(String url) {
        HttpClientWrapper hw = new HttpClientWrapper();
        try {
            logger.info("POST请求发送:" + url);
            setParams(url, hw);
            ResponseMessage message = hw.postNV(url);
            if (message != null && logger.isInfoEnabled()) logger.info("POST返回结果：{}", message.getContent());
            return message;
        } catch (Exception e) {
            throw new RuntimeException("POST请求发送失败:" + url, e);
        }
    }

    /**
     * 将参数拼装在url中，进行post请求。
     * @param url
     * @return
     */
    public static ResponseMessage post(String url, Map<String, Object> paramsMap) {
        try {
            logger.info("POST请求发送:{} , 参数：{} ", url, JacksonUtils.toJson(paramsMap, false));
            HttpClientWrapper hw = getClient(url, paramsMap, socketTimeout, connectTimeout, connectionRequestTimeout);
            ResponseMessage message = hw.postNV(url);
            if (message != null && logger.isInfoEnabled()) logger.info("POST返回结果：{}", message.getContent());
            return message;
        } catch (Exception e) {
            throw new RuntimeException("POST请求发送失败:" + url, e);
        }
    }

    /**
     * 将参数拼装在url中，进行post请求。
     * @param url
     * @return
     */
    public static ResponseMessage post(String url, Map<String, Object> paramsMap, Integer socketTimeout1,
                                       Integer connectTimeout1, Integer connectionRequestTimeout1) {
        try {
            logger.info("POST请求发送:" + url + " 参数：" + JacksonUtils.toJson(paramsMap, false));
            HttpClientWrapper hw = getClient(url, paramsMap, socketTimeout1, connectTimeout1, connectionRequestTimeout1);
            ResponseMessage message = hw.postNV(url);
            if (message != null && logger.isInfoEnabled()) logger.info("POST返回结果：{}", message.getContent());
            return message;
        } catch (Exception e) {
            throw new RuntimeException("POST请求发送失败:" + url, e);
        }
    }

    /**
     * 上传文件（包括图片）
     * @param url       请求URL
     * @param paramsMap 参数和值
     * @return
     */
    public static ResponseMessage postForm(String url, Map<String, Object> paramsMap) {
        try {
            logger.info("POST请求发送:" + url + " 参数：" + JacksonUtils.toJson(paramsMap, false));
            HttpClientWrapper hw = getClient(url, paramsMap, socketTimeout, connectTimeout, connectionRequestTimeout);
            ResponseMessage message = hw.postEntity(url);
            if (message != null && logger.isInfoEnabled()) logger.info("POST返回结果：{}", message.getContent());
            return message;
        } catch (Exception e) {
            throw new RuntimeException("POST请求发送失败:" + url, e);
        }
    }

    private static HttpClientWrapper getClient(String url, Map<String, Object> paramsMap, Integer socketTimeout1,
                                               Integer connectTimeout1, Integer connectionRequestTimeout1) {
        socketTimeout1 = socketTimeout1 == null ? socketTimeout : socketTimeout1;
        connectTimeout1 = connectTimeout1 == null ? connectTimeout : connectTimeout1;
        connectionRequestTimeout1 = connectionRequestTimeout1 == null ? connectionRequestTimeout : connectionRequestTimeout1;
        HttpClientWrapper hw = new HttpClientWrapper(connectionRequestTimeout1, connectTimeout1, socketTimeout1);
        setParams(url, hw);
        if (paramsMap != null) {
            Iterator<String> iterator = paramsMap.keySet().iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                Object value = paramsMap.get(key);
                if (value instanceof File) {
                    FileBody fileBody = new FileBody((File) value);
                    hw.getContentBodies().add(fileBody);
                } else if (value instanceof byte[]) {
                    byte[] byteVlue = (byte[]) value;
                    ByteArrayBody byteArrayBody = new ByteArrayBody(byteVlue, key);
                    hw.getContentBodies().add(byteArrayBody);
                } else {
                    if (value != null && !"".equals(value)) {
                        hw.addNV(key, String.valueOf(value));
                    } else {
                        hw.addNV(key, "");
                    }
                }
            }
        }
        return hw;
    }

    /**
     * 使用post方式，发布对象转成的json给Rest服务。
     * @param url
     * @param jsonBody
     * @return
     */
    public static ResponseMessage postJson(String url, String jsonBody) {
        return postJson(url, jsonBody, socketTimeout, connectTimeout, connectionRequestTimeout);
    }

    /**
     * 使用post方式，发布对象转成的json给Rest服务。
     * @param url
     * @param jsonBody
     * @return
     */
    public static ResponseMessage postJson(String url, String jsonBody, Integer socketTimeout1,
                                           Integer connectTimeout1, Integer connectionRequestTimeout1) {
        try {
            logger.info("JSON POST请求发送:{} , 参数：{} ", url, jsonBody);
            HttpClientWrapper hw = new HttpClientWrapper(connectionRequestTimeout1, connectTimeout1, socketTimeout1);
            ResponseMessage message = hw.postForJson(url, jsonBody, "application/json");
            if (message != null && logger.isInfoEnabled()) logger.info("POST返回结果：{}", message.getContent());
            return message;
        } catch (Exception e) {
            throw new BusinessException("POST请求发送失败", e);
        }
    }

    /**
     * 使用post方式，发布对象转成的xml给Rest服务
     * @param url     URL地址
     * @param xmlBody xml文本字符串
     * @return ResponseMessage 正常则返回ResponseContent对象
     */
    public static ResponseMessage psotXml(String url, String xmlBody) {
        try {
            return postEntity(url, xmlBody, "application/xml");
        } catch (Exception e) {
            throw new RuntimeException("XML POST请求发送失败:" + url, e);
        }
    }

    //-------------------------------------  Private Method

    private static ResponseMessage postEntity(String url, String body, String contentType) throws IOException, RuntimeException, HttpException {
        HttpClientWrapper hw = new HttpClientWrapper();
        hw.addNV("body", body);
        return hw.postNV(url, contentType);
    }

    private static void setParams(String url, HttpClientWrapper hw) {
        String[] paramStr = url.split("[?]", 2);
        if (paramStr == null || paramStr.length != 2) {
            return;
        }
        String[] paramArray = paramStr[1].split("[&]");
        if (paramArray == null) {
            return;
        }
        for (String param : paramArray) {
            if (param == null || "".equals(param.trim())) {
                continue;
            }
            String[] keyValue = param.split("[=]", 2);
            if (keyValue == null || keyValue.length != 2) {
                continue;
            }
            hw.addNV(keyValue[0], keyValue[1]);
        }
    }

    private final static Logger logger = LoggerFactory.getLogger(Https.class);

    //----------------------------------- Test Method
    public static void main(String[] args) {
//        testPost();
        testGet();
        //testUploadFile();
//        String str = "http://localhost:8080/recom/v2/portal/getWebPortalNews";
//        Map<String, Object> params = new HashMap<String, Object>();
//        params.put("memId", "");
//        params.put("pageNum", 1);
//        params.put("pageSize", 20);
//        params.put("queryType", 2);
//        System.out.println(get(str, params).getContent());
    }

    //test
    public static void testGet() {
        String url = "http://www.baidu.com/";
        ResponseMessage responseMessage = get(url);
        System.out.println(responseMessage.getContent());
    }

    private static void testPost() {
        String url = "http://localhost:8080/bdm/loga/insertSendLog";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("sendLog", "测试角色");
        params.put("rolename", "测试角色");
        params.put("id", "119");
        ResponseMessage responseMessage = post(url, params);
        System.out.println(responseMessage.getContent());
    }

    //test
    public static void testUploadFile() {
        try {
            String url = "http://localhost:8280/jfly/action/admin/user/upload.do";
            Map<String, Object> paramsMap = new HashMap<String, Object>();
            paramsMap.put("userName", "jj");
            paramsMap.put("password", "jj");
            paramsMap.put("filePath", new File("C:\\Users\\yangjian1004\\Pictures\\default (1).jpeg"));
            ResponseMessage ret = post(url, paramsMap);
            System.out.println(ret.getContent());
        } catch (Exception e) {
            logger.error("请求发送失败:", e);
            throw new RuntimeException("请求发送失败:", e);
        }
    }

}