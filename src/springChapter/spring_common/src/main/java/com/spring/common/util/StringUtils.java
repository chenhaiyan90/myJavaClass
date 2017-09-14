package com.spring.common.util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @desc: TODO describe what you do
 * @User: ambitor_luo
 * @Date: 2015/8/5
 */
public class StringUtils extends org.apache.commons.lang.StringUtils {
    private final static Logger LOG = LoggerFactory.getLogger(StringUtils.class);

    /**
     * 除掉字符串拼接的时候 多余的,分隔符
     * @param target
     * @return
     */
    public static final String removeExcessSeparator(String target) {
        return removeExcessSeparator(target, ",");
    }


    /**
     * 除掉字符串拼接的时候 多余的分隔符
     * @param target
     * @param separator
     * @return
     */
    public static final String removeExcessSeparator(String target, String separator) {
        if (target == null) return "";
        if (separator == null) return target;
        String temp = target.replaceAll(separator, "").trim();//这种情况,,,或则,这个情况
        if (isBlank(temp)) return "";
        if (target.startsWith(separator)) target = target.substring(1);//这种情况 ,xx,xx
        if (target.endsWith(separator)) target = target.substring(0, target.length() - 1);//这种情况 xx,xx,
        return target;
    }

    /**
     * 把Collection中Map的某个属性提取出来用，分隔符隔开
     * @return
     */
    public static final String[] extractToArray(List<Map<String, Object>> maps, String key) {
        return extractToArray(maps, key, ",");
    }

    /**
     * 把Collection中Map的某个属性提取出来用分隔符隔开
     * @return
     */
    public static final String[] extractToArray(List<Map<String, Object>> maps, String key, String separator) {
        if (maps == null && maps.size() == 0) return null;
        String[] str = new String[maps.size()];
        for (int i = 0; i < maps.size(); i++) {
            Object value = maps.get(i).get(key);
            str[i] = String.valueOf(value);
        }
        return str;
    }

    /**
     * 把Collection中Map的某个属性提取出来用分隔符隔开
     * @return
     */
    public static final <T> String extractObjectToString(List<T> lists, String key) {
        return extractObjectToString(lists, key, ",");
    }

    /**
     * 把Collection中Map的某个属性提取出来用分隔符隔开
     * @return
     */
    public static final <T> String extractObjectToString(List<T> lists, String key, String separator) {
        if (lists == null || lists.size() == 0) return null;
        String str = "";
        for (int i = 0; i < lists.size(); i++) {
            try {
                Object value = lists.get(i);
                Field field = getField(value.getClass(), key);
                if (!field.getName().equals(key)) continue;
                //私有变量必须先设置Accessible为true
                field.setAccessible(true);
                str += String.valueOf(field.get(value)) + ",";
            } catch (Exception e) {
                throw new RuntimeException("extractToArray() 中反射获取字段失败");
            }
        }
        return str.substring(0, str.lastIndexOf(","));
    }

    /**
     * 把Collection中Map的某个属性提取出来用分隔符隔开
     * @return
     */
    public static final <T> Integer[] extractObjectToIntegerArray(List<T> lists, String key) {
        return extractObjectToIntegerArray(lists, key, ",");
    }

    /**
     * 把Collection中Map的某个属性提取出来用分隔符隔开
     * @return
     */
    public static final <T> Integer[] extractObjectToIntegerArray(List<T> lists, String key, String separator) {
        if (lists == null || lists.size() == 0) return null;
        Integer[] str = new Integer[lists.size()];
        for (int i = 0; i < lists.size(); i++) {
            try {
                Object value = lists.get(i);
                Field field = getField(value.getClass(), key);
                if (!field.getName().equals(key)) continue;
                //私有变量必须先设置Accessible为true
                field.setAccessible(true);
                str[i] = Integer.valueOf(String.valueOf(field.get(value)));
            } catch (Exception e) {
                throw new RuntimeException("extractToArray() 中反射获取字段失败");
            }
        }
        return str;
    }

    /**
     * 把Collection中Map的某个属性提取出来用分隔符隔开
     * @return
     */
    public static final <T> String[] extractObjectToArray(List<T> lists, String key) {
        return extractObjectToArray(lists, key, ",");
    }

    /**
     * 把Collection中Map的某个属性提取出来用分隔符隔开
     * @return
     */
    public static final <T> String[] extractObjectToArray(List<T> lists, String key, String separator) {
        if (lists == null || lists.size() == 0) return null;
        String[] str = new String[lists.size()];
        for (int i = 0; i < lists.size(); i++) {
            try {
                Object value = lists.get(i);
                Field field = getField(value.getClass(), key);
                if (!field.getName().equals(key)) continue;
                //私有变量必须先设置Accessible为true
                field.setAccessible(true);
                str[i] = String.valueOf(field.get(value));
            } catch (Exception e) {
                throw new RuntimeException("extractToArray() 中反射获取字段失败");
            }
        }
        return str;
    }

    /**
     * 获取父类以及当前类字段 key
     * @param value
     * @return
     * @throws NoSuchFieldException
     */
    public static Field getField(Class value, String key) throws NoSuchFieldException {
        Field field = null;
        try {
            field = value.getDeclaredField(key);
        } catch (NoSuchFieldException e) {
            if (value.getClass().getSuperclass() != null) {
                field = value.getSuperclass().getDeclaredField(key);
            } else throw e;
        }
        return field;
    }

    /**
     * 获取父类以及当前类所有字段
     * @param value
     * @return
     * @throws NoSuchFieldException
     */
    public static Field[] getField(Class value) {
        Field[] result = null;
        Field[] fields = value.getDeclaredFields();
        if (value.getClass().getSuperclass() != null) {
            Field[] parentFields = value.getSuperclass().getDeclaredFields();
            result = new Field[parentFields.length + fields.length];
            System.arraycopy(fields, 0, result, 0, fields.length);
            System.arraycopy(parentFields, 0, result, fields.length, parentFields.length);
        } else {
            result = fields;
        }
        return result;
    }

    /**
     * 将null值转成"",非null值返回转换为String型的原值
     * @param obj 可为String,Integer,Long,Double,Float,Short,Byte,Character,BigDecimal,Boolean类型
     * @return　""或转换为String型的原值
     */
    public static final String null2Blank(Object obj) {
        try {
            if (obj instanceof String) {
                return null == obj || obj.toString().trim().equalsIgnoreCase("null") ? "" : obj.toString().trim();
            }
            if (obj instanceof Integer) {
                return null == obj || (Integer.valueOf(obj.toString().trim()) == 0) ? "" : obj.toString().trim();
            }
            if (obj instanceof Long) {
                return null == obj || (Long.valueOf(obj.toString().trim()) == 0L) ? "" : obj.toString().trim();
            }
            if (obj instanceof Double) {
                return null == obj || (Double.valueOf(obj.toString().trim()) == 0.0D) ? "" : obj.toString().trim();
            }
            if (obj instanceof Float) {
                return null == obj || (Float.valueOf(obj.toString().trim()) == 0.0F) ? "" : obj.toString().trim();
            }
            if (obj instanceof Short) {
                return null == obj || Short.valueOf(obj.toString().trim()) == 0 ? "" : obj.toString().trim();
            }
            if (obj instanceof Byte) {
                return null == obj || Byte.valueOf(obj.toString().trim()) == 0 ? "" : obj.toString().trim();
            }
            if (obj instanceof Character) {
                return null == obj || obj.toString().trim().equalsIgnoreCase("0") ? "" : obj.toString().trim();
            }
            if (obj instanceof BigDecimal) {
                return null == obj || obj.toString().trim().equalsIgnoreCase("0") ? "" : obj.toString().trim();
            }
            if (obj instanceof Boolean) {
                return null == obj || obj.toString().trim().equalsIgnoreCase("false") ? "" : obj.toString().trim();
            }
        } catch (Exception ex) {
            LOG.error("=====common-core StringUtils null2Blank exception:" + ex);
            ex.printStackTrace();
        }

        return "";
    }

    /**
     * 转义Like通配符  <br/>
     * <b>&nbsp;&nbsp;&nbsp;&nbsp;注意事项：一定要在加左右通配符'%'之前进行转义</b>
     * @param str
     * @return
     * @author charles_shang
     * @date 2016年5月13日
     */
    public static final String escapeSqlLike(String str) {
        if (str == null) {
            return null;
        }

        if (str.contains("\\")) {
            str = str.replace("\\", "\\\\");
        }

        if (str.contains("_")) {
            str = str.replace("_", "\\_");
        }

        if (str.contains("%")) {
            str = str.replace("%", "\\%");
        }

        if (str.contains("/")) {
            str = str.replace("/", "\\/");
        }

        return str;
    }

    /**
     * 判断字符串是否为空
     */
    public static boolean isEmpty(String str) {
        return null == str || "".equals(str.trim());
    }

    /**
     * 字符串数组转字符串
     * @param list
     * @param separator
     * @return
     */
    public static String listToStr(List<String> list, char separator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) sb.append(separator);
            sb.append(list.get(i));
        }
        return sb.toString().substring(0, sb.toString().length());
    }

    /**
     * remove blank before or after a slash
     * @param source
     * @return
     */
    public static String removeBlankAroundSlash(String source) {
        Pattern pattern = Pattern.compile("^\\s*|\\s*$");
        Matcher re = pattern.matcher(source);
        source = re.replaceAll("");
        pattern = Pattern.compile("\\s*\\/\\s*|\\s*\\/|\\/\\s*");
        re = pattern.matcher(source);
        source = re.replaceAll("/");
        return source;
    }

    /**
     * 获取uuid
     * @return
     * @Description:
     */
    public static String getUUID(Integer length) {

        String uuid = UUID.randomUUID().toString();
        String uuidStr = uuid.toString().replace("-", "");

        if (length != null && length > 0) {
            uuidStr = uuidStr.substring(0, length);
        }
        return uuidStr;
    }

    /**
     * 将GBK编码的字符转成UTF-8
     * @param gbkStr
     * @return
     */
    public static String gbk2UTF8(String gbkStr) {
        if (null != gbkStr && !"".equals(gbkStr)) {
            try {
                return new String(gbkStr.getBytes("gbk"), "utf-8");
            } catch (UnsupportedEncodingException e) {
                LOG.error("convert gbk character to utf-8 error, gbkStr=" + gbkStr);
                return null;
            }
        }
        return null;
    }
    
    /**
     * 
     * getStrFromMap:Map转字符串拼接. <br/>
     *
     * @param params
     * @return
     * @since JDK 1.7
     */
    public static String getStrFromMap(Map<String, String> params) {
		   Map<String, String> sortMap = new TreeMap<String, String>();
		   sortMap.putAll(params);
		   // 以k1=v1&k2=v2...方式拼接参数
		   StringBuilder builder = new StringBuilder();
		   for (Map.Entry<String, String> s : sortMap.entrySet()) {
		       String k = s.getKey();
		       String v = s.getValue();
		       if (StringUtils.isBlank(v)) {// 过滤空值
		           continue;
		       }
		       builder.append(k).append("=").append(v).append("&");
		   }
		   if (!sortMap.isEmpty()) {
		       builder.deleteCharAt(builder.length() - 1);
		   }	
		   return builder.toString();
	}
 
	/**
	 * 
	 * @param plainText
	 *            明文
	 * @return 32位密文
	 */
	public static String encryption(String plainText) {
		String re_md5 = new String();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes("utf-8"));
			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}

			re_md5 = buf.toString();

		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return re_md5;
	}

    public static void main(String[] args) {
        String a = "1111111a1111111a111";
        System.out.println(a.replace("a", "b"));
    }
    
}
