package com.spring.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContextUtils implements ApplicationContextAware {

    public static <T> T getBean(String beanName, Class<T> clzz) {

        return applicationContext.getBean(beanName, clzz);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        SpringContextUtils.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {

        return applicationContext;
    }

    private static ApplicationContext applicationContext; // Spring应用上下文环境
}
