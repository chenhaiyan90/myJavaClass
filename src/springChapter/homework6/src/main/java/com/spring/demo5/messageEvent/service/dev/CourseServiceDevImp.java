package com.spring.demo5.messageEvent.service.dev;

import com.spring.demo5.messageEvent.model.TCourse;
import com.spring.demo5.messageEvent.service.CourseService;
import org.mockito.Mockito;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;

/**
 * Created by 00013810 on 2017/9/14.
 */
@Service
public class CourseServiceDevImp implements CourseService {
    @Override
    public List<TCourse> getAllCourse() {
        List<TCourse> mockedList = mock(List.class);
        TCourse course1 = new TCourse(1,"ppp",23);
        TCourse course2 = new TCourse(2,"ppp",23);
        TCourse course3 = new TCourse(3,"ppp",23);
        TCourse course4 = new TCourse(4,"ppp",23);
        mockedList.addAll(Arrays.asList(new TCourse[]{course1, course2,course3,course4}));
        Mockito.when(mockedList.get(0)).thenReturn(course1);
        Mockito.when(mockedList.get(1)).thenReturn(course2);
        Mockito.when(mockedList.get(2)).thenReturn(course3);
        Mockito.when(mockedList.get(3)).thenReturn(course4);
        Mockito.when(mockedList.size()).thenReturn(4);
        return mockedList;
    }
}
