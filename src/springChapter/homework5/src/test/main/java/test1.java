/**
 * Created by 00013810 on 2017/9/13.
 */

import com.spring.demo5.messageEvent.service.MyCoureseService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration("classpath*:spring/applicationContext.xml")
@ActiveProfiles("unit_test")
public class test1 {
//    ApplicationContext ctx = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
    @Autowired
    private MyCoureseService myCoureseService;

    @Test
    @Sql("classpath:test.sql")
    public void addTestData(){
        myCoureseService.getAllCourse().stream().forEach(obj->{System.out.println(obj.toString());});
    }
    @Test
    public void checkDatabasePro(){
        myCoureseService.getAllCourse().stream().forEach(obj->{System.out.println(obj.toString());});
    }

    @Test
    public void checkDatabaseTest(){
        myCoureseService.getAllCourse().stream().forEach(obj->{System.out.println(obj.toString());});
    }
    /*MyCoureseService ldOrderService=ctx.getBean(MyCoureseService.class);
        ldOrderService.getAllCourse().stream().forEach(obj->{System.out.println(obj.toString());});
        Thread.sleep(5000);*/
}
