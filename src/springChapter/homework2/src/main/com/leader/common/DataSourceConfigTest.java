package main.com.leader.common;

import main.com.leader.bean.MyDataSourceParamBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;


/**
 * Created by thinkpad on 2017/8/27 0027.
 */
@Configuration
//@Profile(value = "dataSource_dev")
@PropertySource(value = "classpath:dataSource_test.properties")
public class DataSourceConfigTest {
    public DataSourceConfigTest() {
    }
    @Autowired
    public Environment environment;

    @Bean
    MyDataSourceParamBean dataSourceBean(){
        MyDataSourceParamBean bean = new MyDataSourceParamBean();
        bean.setDatabases(environment.getProperty("database"));
        System.out.println(environment.getProperty("database"));
        bean.setUrl(environment.getProperty("url"));
        bean.setPassword(environment.getProperty("password"));
        bean.setUserName(environment.getProperty("username"));
        return bean;
    }



}
