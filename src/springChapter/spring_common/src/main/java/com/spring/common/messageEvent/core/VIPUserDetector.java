package com.spring.common.messageEvent.core;

import com.spring.common.messageEvent.event.UserEvent;
import com.spring.common.messageEvent.model.LDOrder;
import com.spring.common.messageEvent.service.UserCareService;
import com.spring.common.messageEvent.service.VipUserService;
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
//	public void handlerEvent(MycommonEvent2 commonEvnt)
//	{
//		System.out.println("received event2 "+commonEvnt);
//	}
}