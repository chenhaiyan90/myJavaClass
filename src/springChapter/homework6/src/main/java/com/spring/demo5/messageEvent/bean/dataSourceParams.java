package com.spring.demo5.messageEvent.bean;

import org.springframework.stereotype.Component;

/**
 * Created by 00013810 on 2017/9/13.
 */
@Component
public class dataSourceParams {
    private String url;
    private String user;
    private String password;
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
