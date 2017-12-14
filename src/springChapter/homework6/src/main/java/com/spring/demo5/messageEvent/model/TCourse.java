package com.spring.demo5.messageEvent.model;

/**
 * Created by 00013810 on 2017/9/13.
 */
public class TCourse {
    private Integer id;
    private String name;

    public TCourse() {
    }

    public TCourse(Integer id, String name, Integer mark) {
        this.id = id;
        this.name = name;
        this.mark = mark;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    private Integer mark;

    @Override
    public String toString() {
        return "TCourse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", mark=" + mark +
                '}';
    }
}
