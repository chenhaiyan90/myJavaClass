package com.spring.demo4.messageEvent.service;

import com.spring.demo4.messageEvent.model.LDOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by 00013810 on 2017/9/6.
 */
@Service
public class LDOrderService {
    @Autowired
    private LDOrder ldOrder;
    @Autowired
    private ApplicationEventPublisher publisher;

    public LDOrder createOrder(String id,String name,String email,Integer amount){
        ldOrder.setName(name);
        ldOrder.setId(id);
        ldOrder.setAmount(amount);
        ldOrder.setCreater("chenhaiyan");
        ldOrder.setDesc("test");
        ldOrder.setEmail(email);
        ldOrder.setCreateTime(new Date());
        publisher.publishEvent(ldOrder);
        return ldOrder;
    }
}
