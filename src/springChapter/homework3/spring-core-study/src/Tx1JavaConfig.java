import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@ComponentScan("tx")
public class Tx1JavaConfig {

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
