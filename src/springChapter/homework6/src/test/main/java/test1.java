/**
 * Created by 00013810 on 2017/9/13.
 */

import com.spring.demo5.messageEvent.model.TCourse;
import com.spring.demo5.messageEvent.service.CourseService;
import com.spring.demo5.messageEvent.service.MyCoureseService;
import com.spring.demo5.messageEvent.service.production.CustomBeanPostProcessor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@ContextConfiguration("classpath*:spring/applicationContext.xml")
@ActiveProfiles("produce")
public class test1 {
//    ApplicationContext ctx = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
    @Autowired
    private CourseService courseService;

    @Test
//    @Sql("classpath:test.sql")
    public void addTestData(){
    }
    @Test
    public void checkDatabasePro(){
        courseService.getAllCourse().stream().forEach(obj->{System.out.println(obj.toString());});
    }

    @Test
    public void checkDatabaseTest(){
        List<TCourse> courses = courseService.getAllCourse();
        for (int i = 0; i <courses.size() ; i++) {
            System.out.println(courses.get(i));
        }
    }
    /*MyCoureseService ldOrderService=ctx.getBean(MyCoureseService.class);
        ldOrderService.CourseService().stream().forEach(obj->{System.out.println(obj.toString());});
        Thread.sleep(5000);*/

}
