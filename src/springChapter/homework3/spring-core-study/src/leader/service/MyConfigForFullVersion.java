package leader.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import leader.bean.MyDataSourceParamBean;
import leader.intf.MyMenuService;
@Profile(value="full_version")
@Configuration
@PropertySource(value="classpath:myapp-fullversion.properties")
public class MyConfigForFullVersion {
	public MyConfigForFullVersion()
	{
		System.out.println("@Configuration MyConfigForFullVersion created " );
	}
	@Autowired
	Environment env;	
	
	@Bean  MyDataSourceParamBean myDataSourceParamBean()
	{
		
		MyDataSourceParamBean bean=new MyDataSourceParamBean();
		///bean.setUrl(url);
		System.out.println("get store file path:"+env.getProperty("system.storefile"));
		return bean;
		
	}
	@Bean
	MyMenuService myMenuService() {
		System.out.println("MyConfigForFullVersion created MyMenuService4FullVersion ");
		return new MyMenuService4FullVersion();
	}
}

class MyMenuService4FullVersion implements MyMenuService {

	public String[] getMenus(String userRole) {
		return new String[] { "login", "create","save", "exit" };
	}

}