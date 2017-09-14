package com.spring.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class PropertyReader {

    private PropertyReader(String packageName, Locale locale) {
        ResourceBundle bnd = null;
        try {
            bnd = ResourceBundle.getBundle(packageName, locale);
        } catch (MissingResourceException ex) {
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            if (cl != null) {
                try {
                    bnd = ResourceBundle.getBundle(packageName, locale, cl);
                } catch (MissingResourceException ex2) {
                    // Ignore
                    logger.error("PropertyReader Error:", ex2);
                }
            }
        }
        bundle = bnd;
    }

    public static final PropertyReader getResourceBundle(String packageName) {
        return PropertyReader.getResourceBundle(packageName, Locale.getDefault());
    }


    public static final PropertyReader getResourceBundle(String packageName, Locale locale) {
        PropertyReader sm = rsBuddle.get(packageName);
        if (sm == null) {
            synchronized (PropertyReader.class) {
                if (rsBuddle.get(packageName) == null) {
                    sm = new PropertyReader(packageName, locale);
                    if (sm == null) return null;
                    rsBuddle.put(packageName, sm);
                }
            }
        }
        return sm;
    }

    public final String getString(int key) {
        return getString(String.valueOf(key));
    }


    public final String getString(String key) {
        if (key == null) {
            throw new IllegalArgumentException("key may not have a null value ");
        }
        try {
            String afterMatcher = new String(bundle.getString(key).getBytes("ISO-8859-1"), "UTF-8");
            //逐层遍历解析${}表达式
            while (isMacherVal(afterMatcher)) {
                List<String> str = getMatcherKey(afterMatcher);
                for (String $key : str) {
                    String keyWith$ = "${" + $key + "}";
                    String $value = getString($key);
                    afterMatcher = afterMatcher.replace(keyWith$, $value);
                }
            }
            return afterMatcher;
        } catch (MissingResourceException e) {
            return null;
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public final Integer getInteger(String key) {
        String value = getString(key);
        if (value != null) return Integer.parseInt(value);
        return null;
    }

    /**
     * 解析获取字符串中${}所包裹key
     * @param matcher
     * @return
     * @author ambitor_luo
     * @date 2016年4月8日
     */
    private List<String> getMatcherKey(String matcher) {
        Pattern pt = Pattern.compile(regx);
        Matcher mc = pt.matcher(matcher);
        List<String> str = new ArrayList<String>();
        while (mc.find()) {
            str.add(mc.group(1));
        }

        return str;
    }

    /**
     * 是否含有${}表达式
     * @param matcher
     * @return
     * @author ambitor_luo
     * @date 2016年4月8日
     */
    private Boolean isMacherVal(String matcher) {
        Pattern pt = Pattern.compile(regx);
        Matcher mc = pt.matcher(matcher);

        return mc.find();
    }

    private static int LOCALE_CACHE_SIZE = 10;
    private final ResourceBundle bundle;
    private Logger logger = LoggerFactory.getLogger(PropertyReader.class);
    //解析${}表达式正则匹配符
    private static final String regx = "\\$\\{(.*?)\\}";
    private static Map<String, PropertyReader> rsBuddle = new LinkedHashMap<String, PropertyReader>(LOCALE_CACHE_SIZE, 1, true) {
        private static final long serialVersionUID = 1L;

        @Override
        protected boolean removeEldestEntry(Map.Entry<String, PropertyReader> eldest) {
            if (size() > (LOCALE_CACHE_SIZE - 1)) {
                return true;
            }
            return false;
        }
    };

}
