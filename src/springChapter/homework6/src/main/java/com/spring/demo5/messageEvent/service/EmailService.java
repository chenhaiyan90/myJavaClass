package com.spring.demo5.messageEvent.service;

import org.springframework.stereotype.Service;

/**
 * Created by 00013810 on 2017/9/6.
 */
@Service
public class EmailService {
    public void sendEmailToUser(String name,String email){
        System.out.println(String.format("Dear %s :congratulations ! to be our vip ",name));
    }
}
