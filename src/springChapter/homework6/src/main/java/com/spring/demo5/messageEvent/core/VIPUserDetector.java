package com.spring.demo5.messageEvent.core;

import com.spring.demo5.messageEvent.event.UserEvent;
import com.spring.demo5.messageEvent.model.LDOrder;
import com.spring.demo5.messageEvent.service.VipUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Created by 00013810 on 2017/9/6.
 */
@Component
public class VIPUserDetector {

    @Autowired
    private VipUserService vipUserService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @EventListener(value={LDOrder.class},condition = "#object.amount > 500")
    public void handlerEvent(Object object)
    {
        System.out.println("received order amount >500 "+object);
        if(object instanceof LDOrder){
            LDOrder ldOrder=(LDOrder) object;
            UserEvent userEvent=vipUserService.addUser(ldOrder.getName(), ldOrder.getEmail());
            publisher.publishEvent(userEvent);
        }
    }

//	@EventListener
//	public void handlerEvent(MyDemoEvent2 demoEvnt)
//	{
//		System.out.println("received event2 "+demoEvnt);
//	}
}