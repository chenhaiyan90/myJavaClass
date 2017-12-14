package com.spring.demo5.messageEvent.service;

import com.spring.demo5.messageEvent.model.TCourse;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 00013810 on 2017/9/14.
 */
@Service
public interface CourseService {
    public List<TCourse> getAllCourse();
}
