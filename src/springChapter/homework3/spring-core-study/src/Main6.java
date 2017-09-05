import java.util.Arrays;

import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.env.Environment;

import leader.intf.MyMenuService;
import leader.service.MyDataSource;

public class Main6 {

	public static void main(String[] args) throws InterruptedException {
		System.setProperty("spring.profiles.active", "full_version");
		System.setProperty("spring.profiles.active", "full_version,free_version");
		System.setProperty("spring.profiles.default", "free_version");
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext("applicationContext2.xml");
		Environment env = ctx.getEnvironment();
		System.out.println(env.getClass().getName());
		System.out.println("getDefaultProfiles " +Arrays.toString(env.getDefaultProfiles()));
		System.out.println("getActiveProfiles " + Arrays.toString(env.getActiveProfiles()));
		// ctx.getEnvironment().setActiveProfiles("local_dev");
		// ctx.getEnvironment().setDefaultProfiles("local_dev");
		 MyDataSource ds=ctx.getBean(MyDataSource.class);
		 MyMenuService menuSrv=ctx.getBean(MyMenuService.class);
		 System.out.println("menus  "+Arrays.toString(menuSrv.getMenus("user")));
		ctx.close();

	}
}