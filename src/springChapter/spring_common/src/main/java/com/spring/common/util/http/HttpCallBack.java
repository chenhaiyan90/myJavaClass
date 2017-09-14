package com.spring.common.util.http;

import com.spring.common.exception.BusinessException;
import com.spring.common.util.JacksonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description： Http请求服务，回调处理
 * Created by Ambitor on 2016/8/24.
 */
public class HttpCallBack {

    public static <T> T callback(String url, String requestBody, ServiceHandler<T> serviceHandler, boolean retryOnError) {
        return callback(url, requestBody, serviceHandler, retryOnError, defaultParamKeys);
    }

    public static <T> T callback(String url, String requestBody, ServiceHandler<T> serviceHandler, boolean retryOnError, Map<String, String> paramKeys) {
        ResponseMessage response = null;
        try {
            response = Https.postJson(url, requestBody);
        } catch (BusinessException e) {
            if (retryOnError) {
                logger.error("接口调用出错，即将重试第二次...");
                try {
                    response = Https.postJson(url, requestBody);
                } catch (BusinessException e1) {
                    logger.error("接口调用出错，即将重试第三次...");
                    response = Https.postJson(url, requestBody);
                }
            } else {
                throw e;
            }
        }
        return callback(response, serviceHandler, paramKeys);
    }

    public static <T> T callback(String url, String requestBody, ServiceHandler<T> serviceHandler) {
        ResponseMessage response = Https.postJson(url, requestBody);
        return callback(response, serviceHandler, defaultParamKeys);
    }

    public static <T> T callback(String url, String requestBody, ServiceHandler<T> serviceHandler, Map<String, String> paramKeys) {
        ResponseMessage response = Https.postJson(url, requestBody);
        return callback(response, serviceHandler, paramKeys);
    }

    public static <T> T callback(ResponseMessage response, ServiceHandler<T> serviceHandler) {
        return callback(response, serviceHandler, defaultParamKeys);
    }

    public static <T> T callback(ResponseMessage response, ServiceHandler<T> serviceHandler, Map<String, String> paramKeys) {
        Map<String, Object> map = JacksonUtils.toBean(response.getContent(), Map.class);
        if (!map.containsKey(paramKeys.get("errcode")))
            throw new BusinessException("-----------接口返回不是json格式，或不包含errcode.");
        Object errCode = map.get(paramKeys.get("errcode"));
        serviceHandler.setParamKeys(paramKeys);
        if (String.valueOf(errCode).equals("0")) {
            if (serviceHandler.getT() == null || serviceHandler.getT() == String.class)
                return (T) JacksonUtils.toJson(map.get(paramKeys.get("data")), false);
            if (serviceHandler.getE() != null) {
                return serviceHandler.doSuccessful(JacksonUtils.toBean(map.get(paramKeys.get("data")), serviceHandler.getT(), serviceHandler.getE()));
            } else {
                return serviceHandler.doSuccessful(JacksonUtils.toBean(map.get(paramKeys.get("data")), serviceHandler.getT()));
            }
        } else {
            serviceHandler.doFail(map);
            return null;
        }
    }

    public static class ServiceHandler<T> {
        protected Class t;
        protected Class e;
        private Map<String, String> paramKeys;

        public ServiceHandler() {
            Object calss = this.getClass().getGenericSuperclass();
            if (calss instanceof ParameterizedType) {
                ParameterizedType genType = (ParameterizedType) this.getClass().getGenericSuperclass();
                Type[] params = genType.getActualTypeArguments();
                if (params[0] instanceof ParameterizedTypeImpl) {
                    t = ((ParameterizedTypeImpl) params[0]).getRawType();
                    Type[] tParams = ((ParameterizedTypeImpl) params[0]).getActualTypeArguments();
                    //只支持两层泛型
                    if (tParams[0] instanceof ParameterizedTypeImpl) {
                        //if type is List<Map<String,Object>>...
                        e = ((ParameterizedTypeImpl) tParams[0]).getRawType();
                    } else {
                        // if type is List<Bean>...
                        if (t != Map.class) e = (Class) tParams[0];
                    }
                } else if (params[0] instanceof Class) {
                    t = (Class) params[0];
                }
            }
        }

        public T doSuccessful(T data) {
            return data;
        }

        public void doFail(Map<String, Object> map) {
            logger.info("HTTP接口业务逻辑出错.");
            String code = String.valueOf(map.get(paramKeys.get("errcode")));
            String msg = String.valueOf(map.get(paramKeys.get("errmsg")));
            throw new BusinessException(Integer.parseInt(code), msg);
        }

        public Class<T> getT() {
            return t;
        }

        public Class<T> getE() {
            return e;
        }

        public Map<String, String> getParamKeys() {
            return paramKeys;
        }

        public void setParamKeys(Map<String, String> paramKeys) {
            this.paramKeys = paramKeys;
        }
    }

    private static Logger logger = LoggerFactory.getLogger(ServiceHandler.class);

    private final static Map<String, String> defaultParamKeys = new HashMap<String, String>() {
        {
            put("errcode", "errcode");
            put("data", "data");
            put("errmsg", "errmsg");
        }
    };
}
