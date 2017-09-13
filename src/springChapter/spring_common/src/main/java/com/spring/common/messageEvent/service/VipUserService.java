package com.spring.common.messageEvent.service;

import com.spring.common.messageEvent.event.UserEvent;
import org.springframework.stereotype.Service;

/**
 * Created by 00013810 on 2017/9/6.
 */
@Service
public class VipUserService {


    public UserEvent addUser(String name,String email){
        UserEvent userEvent = new UserEvent("dds",name, email);
        userEvent.setName(name);
        userEvent.setEmail(email);
        return userEvent;
    }

}
