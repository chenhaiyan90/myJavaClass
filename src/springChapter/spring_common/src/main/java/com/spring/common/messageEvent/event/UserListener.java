package com.spring.common.messageEvent.event;

import com.spring.common.messageEvent.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Repository;

/**
 * Created by 00013810 on 2017/9/6.
 */
@Repository
public class UserListener implements ApplicationListener {

    @Autowired
    private EmailService emailService;

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if(applicationEvent instanceof UserEvent){
            UserEvent userEvent=(UserEvent) applicationEvent;
            userEvent.print();
            emailService.sendEmailToUser(userEvent.getName(),userEvent.getEmail());
        }
    }
}
