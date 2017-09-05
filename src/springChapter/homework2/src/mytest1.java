import main.com.leader.bean.MyDataSourceParamBean;
import main.com.leader.common.DataSourceConfigTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.env.Environment;

import java.util.Arrays;

/**
 * Created by thinkpad on 2017/8/27 0027.
 */
public class mytest1 {
    @Autowired
    public DataSourceConfigTest dataSource_dev;



    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(DataSourceConfigTest.class);
        MyDataSourceParamBean test = (MyDataSourceParamBean) ctx.getBean("dataSourceBean");


        System.out.println(test.toString());

        mytest1 mytest1=new mytest1();
        System.out.println(mytest1.dataSource_dev);

        mytest1 mytest2=new mytest1();
        System.out.println(mytest1.dataSource_dev);
      /*  mytest1 mytest1=new mytest1();
        System.out.println(mytest1.getClass().getResource("/").getPath());
        System.setProperty("spring.profiles.active", "full_version");
        System.setProperty("spring.profiles.active", "full_version,free_version");
        System.setProperty("spring.profiles.default", "free_version");
        GenericXmlApplicationContext ctx = new GenericXmlApplicationContext("applicationContext2.xml");
        Environment env = ctx.getEnvironment();
        System.out.println(env.getClass().getName());
        System.out.println("getDefaultProfiles " + Arrays.toString(env.getDefaultProfiles()));
        System.out.println("getActiveProfiles " + Arrays.toString(env.getActiveProfiles()));
        // ctx.getEnvironment().setActiveProfiles("local_dev");
        // ctx.getEnvironment().setDefaultProfiles("local_dev");
        MyDataSourceParamBean ds=ctx.getBean(MyDataSourceParamBean.class);
        System.out.println(ds.toString());*/


    }
}
