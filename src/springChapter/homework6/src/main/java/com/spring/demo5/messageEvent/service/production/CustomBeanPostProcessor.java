/**   
 * @Title: CustomBeanPostProcessor.java 
 * @Package com.denny.spring.extend 
 * @Description: TODO
 * @author Zhangkui zhangkui_java@163.com   
 * @date 2017年9月9日 下午10:38:14 
 * @version V1.0   
 */
package com.spring.demo5.messageEvent.service.production;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

/**
 * @ClassName: CustomBeanPostProcessor
 * @Description: TODO
 * @author Zhangkui zhangkui_java@163.com
 * @date 2017年9月9日 下午10:38:14
 * 
 */
@Component("customBeanPostProcessor")
public class CustomBeanPostProcessor implements BeanPostProcessor {

	private final static Logger logger = LoggerFactory.getLogger(CustomBeanPostProcessor.class);

	@Override
	public Object postProcessAfterInitialization(Object arg0, String arg1) throws BeansException {
		Class clz = arg0.getClass();
		checkField(clz);
		checkInterface(clz);
		return arg0;
	}

	@Override
	public Object postProcessBeforeInitialization(Object arg0, String arg1) throws BeansException {
		return arg0;
	}

	/**
	 * 检查Bean包含的所有(公共、私有、受保护)属性个数.
	 */
	private void checkField(Class<?> clz) {
		// 获取该类的所有属性(包括:public、private、protected);
		Field[] fieldArr = clz.getDeclaredFields();
		StringBuilder strBuilder = new StringBuilder();
		if (fieldArr != null && fieldArr.length > 4) {
			strBuilder.append("warning: " + clz.getName() + " size " + fieldArr.length + " properties.\n");
			for (Field field : fieldArr) {
				strBuilder.append("properties:" + field.getName() + " type is:" + field.getType().getName() + "\n");
			}
			logger.info(strBuilder.toString());
		}
	}

	/**
	 * 检查Bean的接口个数
	 */
	private void checkInterface(Class<?> clz) {
		Class<?>[] interfaces = clz.getInterfaces();
		if (interfaces != null && interfaces.length > 2) {
			StringBuilder strBuilder = new StringBuilder();
			strBuilder.append("警告 " + clz.getName() + " 拥有 " + interfaces.length + " 个接口.\n");
			for (Class<?> interfaceClass : interfaces) {
				strBuilder.append("接口:" + interfaceClass.getName() + "\n");
			}
			logger.info(strBuilder.toString());
		}
	}

}
