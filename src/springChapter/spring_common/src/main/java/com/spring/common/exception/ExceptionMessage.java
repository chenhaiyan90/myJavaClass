package com.spring.common.exception;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

/**
 * 异常信息
 * 
 * @author chenhaiyan
 * @date 2016-03-25
 */

public class ExceptionMessage {
	private static final Logger logger = LoggerFactory.getLogger(ExceptionMessage.class);
	private static Map<Integer, String> msgMap;

	static {
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("exceptions.json");

		try {
			List<String> lines = IOUtils.readLines(is, StandardCharsets.UTF_8);
			StringBuilder sb = new StringBuilder();
			for (String line : lines) {
				sb.append(line);
			}
			msgMap = JSON.parseObject(sb.toString(), new TypeReference<Map<Integer, String>>() {
			});
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			IOUtils.closeQuietly(is);
		}
	}

	/**
	 * 根据异常code查询默认的异常信息（语言为：zh_CN）
	 * 
	 * @param errorCode
	 * @return 异常信息
	 */
	public static String getErrorMessage(Integer errorCode) {
		return msgMap.get(errorCode);
	}

}
