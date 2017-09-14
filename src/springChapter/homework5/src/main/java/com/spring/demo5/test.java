package com.spring.demo5;

import com.spring.demo5.messageEvent.service.LDOrderService;
import com.spring.demo5.messageEvent.service.MyCoureseService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by 00013810 on 2017/9/6.
 */
public class test {
    public static void main(String[] args) throws InterruptedException {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
        MyCoureseService ldOrderService=ctx.getBean(MyCoureseService.class);
        ldOrderService.getAllCourse().stream().forEach(obj->{System.out.println(obj.toString());});
        Thread.sleep(5000);

    }
}
