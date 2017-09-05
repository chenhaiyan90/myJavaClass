import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement(mode=AdviceMode.ASPECTJ)
@EnableLoadTimeWeaving(aspectjWeaving=EnableLoadTimeWeaving.AspectJWeaving.ENABLED)
@ComponentScan("tx")
public class Tx1JavaConfig3 {

	@Bean
	public InstrumentationLoadTimeWeaver loadTimeWeaver()  throws Throwable {
	InstrumentationLoadTimeWeaver loadTimeWeaver = new InstrumentationLoadTimeWeaver();
	return loadTimeWeaver;
	}
	@Bean
	public PlatformTransactionManager getTransactionManager()
	{
		System.out.println("transaction man created ");
		DataSourceTransactionManager txMan=new DataSourceTransactionManager();
		txMan.setDataSource(getDataSource());
		return txMan;
	}
	
	@Bean
	public DriverManagerDataSource getDataSource() {
		System.out.println("datasource created ");
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
		ds.setUrl("jdbc:mysql://localhost:3306/leaderdb?serverTimezone=UTC");
		ds.setUsername("leaderus");
		ds.setPassword("123456");
		return ds;
	}
}
