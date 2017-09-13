package com.spring.demo4.messageEvent.model;

import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * Created by 00013810 on 2017/9/6.
 */
@Repository
public class LDOrder {
    private String id;
    private String name;
    private Integer amount;
    private String status;
    private String desc;
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private Date createTime;
    private String creater;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    @Override
    public String toString() {
        return "LDOrder{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                ", status='" + status + '\'' +
                ", desc='" + desc + '\'' +
                ", email='" + email + '\'' +
                ", createTime=" + createTime +
                ", creater='" + creater + '\'' +
                '}';
    }
}
