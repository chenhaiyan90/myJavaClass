package com.spring.demo4.messageEvent.event;

import org.springframework.context.ApplicationEvent;

/**
 * Created by 00013810 on 2017/9/6.
 */

public class UserEvent extends ApplicationEvent {
    private static final long serialVersionUID = 1L;
    private String name;
    private String age;
    private String email;
    public UserEvent(Object source, String name, String email) {
        super(source);
    }

    public UserEvent(Object source, String name, String age, String email) {
        super(source);
        this.name = name;
        this.age = age;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserEvent{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public void print(){
        System.out.println(toString());
    }
}
