package com.spring.common.util;

import java.text.SimpleDateFormat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
	//private static Logger logger = LoggerFactory.getLogger(JsonUtil.class);

	private static SerializerFeature[] features = {SerializerFeature.WriteNullNumberAsZero, SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.DisableCircularReferenceDetect};  
	
	private static SimpleDateFormat fullPattenr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static ObjectMapper OM = new ObjectMapper();
	static {
		OM.setDateFormat(fullPattenr);
		OM.setSerializationInclusion(Include.NON_NULL);  
	}
//	/**
//	 * 
//	 * @author chenhaiyan
//	 * @param obj
//	 * @return
//	 */
//	public static String object2Json(Object obj) {
//
//		ObjectMapper mapper = new ObjectMapper();
//		String json = "";
//		try {
//			json = mapper.writeValueAsString(obj);
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error("Exception:", e);
//		}
//		return json;
//	}
	
	
	public static String object2Json(Object obj) {
	      return  JSON.toJSONString(obj, features);  
	}
	
	

	/**
	 * 
	 * @author chenhaiyan
	 * @return
	 */
//	public static  ObjectMapper createObjectJsonMapper() {
//		ObjectMapper mapper = new ObjectMapper();
//		mapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);
//		mapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
//		mapper.configure(Feature.IGNORE_UNDEFINED, false);  
//		return mapper;
//	}

}
