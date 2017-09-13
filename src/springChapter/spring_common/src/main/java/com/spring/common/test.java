package com.spring.common;

import com.spring.common.messageEvent.service.LDOrderService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by 00013810 on 2017/9/6.
 */
public class test {
    public static void main(String[] args) throws InterruptedException {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext2.xml");
        LDOrderService ldOrderService=ctx.getBean(LDOrderService.class);
        ldOrderService.createOrder("No001","haiyan1","111@qq.com",300);
        ldOrderService.createOrder("No003","haiyan3","222@qq.com",600);
        ldOrderService.createOrder("No002","haiyan2","111@qq.com",200);
        ldOrderService.createOrder("No004","haiyan4","222@qq.com",800);
        Thread.sleep(5000);


    }
}
