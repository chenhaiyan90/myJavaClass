package com.spring.common.util.tao;

import com.spring.common.exception.BusinessException;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.Collection;
import java.util.Map;

/**
 * @Description： 断言工具，throw BusinessException
 * Created by Ambitor on 2016/11/22.
 */
public class Assert {

    public static void isTrue(boolean expression, String message) {
        if (!expression) throw new BusinessException(message);
    }

    public static void isTrue(boolean expression, Integer code) {
        if (!expression) throw new BusinessException(code);
    }

    public static void isTrue(boolean expression) {
        isTrue(expression, "[校验失败] - 表达式必须为True");
    }

    public static void isNull(Object object, String message) {
        if (object != null) throw new BusinessException(message);
    }

    public static void isNull(Object object, Integer code) {
        if (object != null) throw new BusinessException(code);
    }

    public static void isNull(Object object) {
        isNull(object, "[校验失败] - 参数必须为null.");
    }

    public static void notNull(Object object, String message) {
        if (object == null) throw new BusinessException(message);
    }

    public static void notNull(Object object, Integer code) {
        if (object == null) throw new BusinessException(code);
    }

    public static void notNull(Object object) {
        notNull(object, "[校验失败] - 参数必须不为null.");
    }

    public static void hasLength(String text, String message) {
        if (!org.springframework.util.StringUtils.hasLength(text)) throw new BusinessException(message);
    }

    public static void hasLength(String text, Integer code) {
        if (!org.springframework.util.StringUtils.hasLength(text)) throw new BusinessException(code);
    }

    public static void hasLength(String text) {
        hasLength(text, "[校验失败] - 参数不能为null切不能为空字符串.");
    }

    /**
     * 不等于null & length大于0 & 不是空格 ->true
     * else false
     * @param text
     * @param message
     */
    public static void hasText(String text, String message) {
        if (!org.springframework.util.StringUtils.hasText(text)) throw new BusinessException(message);
    }

    public static void hasText(String text, Integer code) {
        if (!org.springframework.util.StringUtils.hasText(text)) throw new BusinessException(code);
    }

    public static void hasText(String text) {
        hasText(text, "[校验失败] -  参数不能为null切不能空/空字符串.");
    }

    public static void doesNotContain(String textToSearch, String substring, String message) {
        if (org.springframework.util.StringUtils.hasLength(textToSearch) &&
                org.springframework.util.StringUtils.hasLength(substring) &&
                textToSearch.contains(substring))
            throw new BusinessException(message);
    }

    public static void doesNotContain(String textToSearch, String substring, Integer code) {
        if (org.springframework.util.StringUtils.hasLength(textToSearch) &&
                org.springframework.util.StringUtils.hasLength(substring) &&
                textToSearch.contains(substring))
            throw new BusinessException(code);
    }

    public static void doesNotContain(String textToSearch, String substring) {
        doesNotContain(textToSearch, substring, "[校验失败] - 字符串不能包含有 [" + substring + "]");
    }

    public static void notEmpty(Object[] array, String message) {
        if (ObjectUtils.isEmpty(array)) throw new BusinessException(message);
    }

    public static void notEmpty(Object[] array, Integer code) {
        if (ObjectUtils.isEmpty(array)) throw new BusinessException(code);
    }

    public static void notEmpty(Object[] array) {
        notEmpty(array, "[校验失败] - 数组不能为空并至少有一个元素.");
    }

    public static void noNullElements(Object[] array, String message) {
        if (array != null) {
            Object[] var2 = array;
            int var3 = array.length;
            for (int var4 = 0; var4 < var3; ++var4) {
                Object element = var2[var4];
                if (element == null) {
                    throw new BusinessException(message);
                }
            }
        }
    }

    public static void noNullElements(Object[] array, Integer code) {
        if (array != null) {
            Object[] var2 = array;
            int var3 = array.length;
            for (int var4 = 0; var4 < var3; ++var4) {
                Object element = var2[var4];
                if (element == null) {
                    throw new BusinessException(code);
                }
            }
        }

    }

    public static void noNullElements(Object[] array) {
        noNullElements(array, "[校验失败] - 数组不能包含任何为null的元素.");
    }

    public static void notEmpty(Collection<?> collection, String message) {
        if (CollectionUtils.isEmpty(collection)) throw new BusinessException(message);
    }

    public static void notEmpty(Collection<?> collection, Integer code) {
        if (CollectionUtils.isEmpty(collection)) throw new BusinessException(code);
    }

    public static void notEmpty(Collection<?> collection) {
        notEmpty(collection, "[校验失败] - 集合不能为空并至少有一个元素.");
    }

    public static void notEmpty(Map<?, ?> map, String message) {
        if (CollectionUtils.isEmpty(map)) throw new BusinessException(message);
    }

    public static void notEmpty(Map<?, ?> map, Integer code) {
        if (CollectionUtils.isEmpty(map)) throw new BusinessException(code);
    }

    public static void notEmpty(Map<?, ?> map) {
        notEmpty(map, "[校验失败] - Map不能为空并至少有一个元素.");
    }

    public static void isInstanceOf(Class<?> clazz, Object obj) {
        isInstanceOf(clazz, obj, "");
    }

    public static void isInstanceOf(Class<?> type, Object obj, Integer code) {
        notNull(type, "类型不能为空.");
        if (!type.isInstance(obj)) throw new BusinessException(code);
    }

    public static void isInstanceOf(Class<?> type, Object obj, String message) {
        notNull(type, "类型不能为空.");
        if (!type.isInstance(obj))
            throw new BusinessException((org.springframework.util.StringUtils.hasLength(message) ? message + " " : "") + "对象的class [" + (obj != null ? obj.getClass().getName() : "null") + "] 必须是此类型 " + type);
    }

    public static void isAssignable(Class<?> superType, Class<?> subType) {
        isAssignable(superType, subType, "");
    }

    public static void isAssignable(Class<?> superType, Class<?> subType, String message) {
        notNull(superType, "类型不能为空.");
        if (subType == null || !superType.isAssignableFrom(subType))
            throw new BusinessException((org.springframework.util.StringUtils.hasLength(message) ? message + " " : "") + subType + " 不是它的子类 " + superType);
    }

    public static void isAssignable(Class<?> superType, Class<?> subType, Integer code) {
        notNull(superType, "类型不能为空.");
        if (subType == null || !superType.isAssignableFrom(subType)) throw new BusinessException(code);
    }

}
