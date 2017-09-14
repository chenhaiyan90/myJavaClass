package com.spring.common.util;

import com.spring.common.exception.BusinessException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.ReflectionUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @desc: TODO describe what you do
 * @User: ambitor_luo
 * @Date: 2015/6/29
 */
public class JacksonUtils {

    private static ThreadLocal<SimpleDateFormat> local = new ThreadLocal() {
        protected SimpleDateFormat initialValue() {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return simpleDateFormat;
        }
    };
    //把Object转换成Json字符串的mapper的配置
    private static ObjectMapper write = new ObjectMapper() {
        {
            setTimeZone(TimeZone.getTimeZone("GMT+8"));
            setDateFormat(local.get());
            configure(SerializationFeature.INDENT_OUTPUT, Boolean.TRUE);
        }
    };
    //把Object转换成Json字符串的mapper的配置
    private static ObjectMapper writeNoIndent = new ObjectMapper() {
        {
            setDateFormat(local.get());
            setTimeZone(TimeZone.getTimeZone("GMT+8"));
        }
    };
    //把JSON字符串转换成Object的mapper的配置
    private static ObjectMapper read = new ObjectMapper() {
        {
            setDateFormat(local.get());
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, Boolean.FALSE);
        }
    };

    /**
     * Object 转 Json字符串
     * @param object 元素类型
     * @return
     */
    public static String toJson(Object object) {
        return toJson(object, false);
    }

    /**
     * Object 转 Json字符串
     * @param object 元素类型
     * @return
     */
    public static String toJson(Object object, boolean indent) {
        try {
            if (object == null) return null;
            if (object instanceof String) return (String) object;
            if (indent) return write.writeValueAsString(object);
            else return writeNoIndent.writeValueAsString(object);
        } catch (Exception e) {
            throw new BusinessException("对象转JSON出错", e);
        }
    }

    /**
     * JSON 转Object
     * @param json json字符串
     * @param clzz 元素类型
     * @param <T>
     * @return
     */
    public static <T> T toBean(String json, Class<T> clzz) {
        try {
            if (json == null) return null;
            return read.readValue(json, clzz);
        } catch (Exception e) {
            throw new BusinessException("JSON转对象出错：" + json, e);
        }
    }

    /**
     * JSON 转Object
     * @param object json字符串
     * @param clzz   元素类型
     * @param <T>
     * @return
     */
    public static <T> T toBean(Object object, Class<T> clzz) {
        String json = null;
        try {
            if (object == null) return null;
            json = toJson(object);
            return read.readValue(json, clzz);
        } catch (Exception e) {
            throw new BusinessException("JSON转对象出错：" + json, e);
        }
    }

    /**
     * 转带泛型的Bean
     * @param json
     * @param beanClass
     * @param genericClass
     * @param <T>
     * @return
     */
    public static <T> T toGenericBean(String json, Class<T> beanClass, Class<?> genericClass) {
        try {
            JavaType bean = read.getTypeFactory().constructParametrizedType(beanClass, beanClass, genericClass);
            return read.readValue(json, read.getTypeFactory().constructType(bean));
        } catch (IOException e) {
            throw new BusinessException("JSON转对象出错：" + json, e);
        }
    }

    /**
     * 获取泛型的Collection Type
     * @param jsonStr         json字符串
     * @param collectionClass 泛型的Collection
     * @param elementClasses  元素类型
     */
    @SafeVarargs
    public static <T> T toBean(Object jsonStr, Class<T> collectionClass, Class<T>... elementClasses) {
        try {
            JavaType javaType = read.getTypeFactory().constructParametricType(collectionClass, elementClasses);
            return read.readValue(toJson(jsonStr), javaType);
        } catch (Exception e) {
            throw new BusinessException("JSON转对象出错：" + jsonStr, e);
        }
    }

    /**
     * 获取泛型的Collection Type
     * @param jsonStr         json字符串
     * @param collectionClass 泛型的Collection
     * @param elementClasses  元素类型
     */
    @SafeVarargs
    public static <T extends Collection> T toBean(String jsonStr, Class<T> collectionClass, Class<?>... elementClasses) {
        try {
            if (jsonStr == null) return null;
            if (elementClasses[0] != null && elementClasses[0].isAssignableFrom(Map.class)) {
                JavaType map = read.getTypeFactory().constructMapType(HashMap.class, String.class, Object.class);
                return read.readValue(jsonStr, read.getTypeFactory().constructCollectionType(collectionClass, map));
            }
            JavaType javaType = read.getTypeFactory().constructParametricType(collectionClass, elementClasses);
            return read.readValue(jsonStr, javaType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * list<Object> 转 List<Map<String, Object>>
     * @param objs
     * @param <T>
     * @return
     */
    public static <T> List<Map<String, Object>> listToMap(List<T> objs) {
        return listToMap(objs, null);
    }

    /**
     * list<Object> 转 List<Map<String, Object>>
     * @param objs
     * @param exclusive 不要这些属性
     * @param <T>
     * @return
     */
    public static <T> List<Map<String, Object>> listToMap(List<T> objs, List<String> exclusive) {
        if (objs == null) return null;
        List<Map<String, Object>> maps = new ArrayList<>();
        for (Object obj : objs) {
            if (obj == null) continue;
            maps.add(objectToMap(obj, exclusive));
        }
        return maps;
    }

    /**
     * object 转  Map<String, Object>
     * @param obj
     * @return
     */
    public static Map<String, Object> objectToMap(Object obj) {
        return objectToMap(obj, null, null);
    }

    public static Map<String, Object> objectToMap(Object obj, List<String> exclusive) {
        return objectToMap(obj, null, exclusive);
    }

    /**
     * object 转  Map<String, Object>
     * @param obj
     * @return
     */
    public static Map<String, Object> objectToMap(Object obj, String prefix) {
        return objectToMap(obj, prefix, null);
    }

    /**
     * object 转  Map<String, Object>
     * @param obj
     * @return
     */
    public static Map<String, Object> objectToMap(Object obj, String prefix, List<String> exclusive) {
        if (obj == null) return null;
        Map<String, Object> map = new HashMap<>();
        try {
            Field[] declaredFields = getFields(obj);
            for (Field field : declaredFields) {
                if (field == null) continue;
                field.setAccessible(true);
                if (exclusive != null && exclusive.contains(field.getName())) continue;
                Object value = null;
                try {
                    value = PropertyUtils.getProperty(obj, field.getName());
                } catch (Exception e) {
                    //调用get属性为空，忽略，后面通过反射直接获取属性值
                }
                if (StringUtils.isNotBlank(prefix))
                    map.put(prefix + field.getName(), value == null ? field.get(obj) : value);
                else map.put(field.getName(), field.get(obj));
            }
        } catch (IllegalAccessException e) {
            throw new BusinessException("对象转Map报错", e);
        }
        return map;
    }

    /**
     * Map<String, Object> 转 tClass
     * @param map
     * @param tClass
     * @return
     */
    public static <T> T mapToObject(Map<String, Object> map, Class<T> tClass) {
        if (map == null) return null;
        try {
            T t = tClass.newInstance();
            for (String key : map.keySet()) {
                Field field = tClass.getDeclaredField(key);
                ReflectionUtils.setField(field, t, map.get(key));
            }
            return t;
        } catch (Exception e) {
            throw new BusinessException("Map转对象报错", e);
        }
    }

    public static Field[] getFields(Object obj) {
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        if (obj.getClass().getSuperclass() != null) {
            Field[] superFields = obj.getClass().getSuperclass().getDeclaredFields();
            Field[] result = Arrays.copyOf(declaredFields, declaredFields.length + superFields.length);
            System.arraycopy(superFields, 0, result, declaredFields.length, superFields.length);
            return result;
        }
        return declaredFields;
    }

}
