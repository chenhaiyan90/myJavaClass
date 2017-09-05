package leader.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.mockito.Mockito;
import org.mockito.internal.stubbing.answers.DoesNothing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;

@Profile(value = "unit_test")
@Configuration
@PropertySource(value = "classpath:myapp-fullversion.properties")
public class MyConfigForUnitTest {
	public MyConfigForUnitTest() {
		System.out.println("@Configuration MyConfigForUnitTest created ");
	}

	@Autowired
	Environment env;
	@Bean
	public PlatformTransactionManager  transactionManager()
	{//TransactionalTestExecutionListener
		System.out.println("get PlatformTransactionManager ");
		 
		MyTraceTransactionManager transMan=new MyTraceTransactionManager();
		transMan.setDataSource(dataSource());
		return transMan;
	}
	@Bean
	public DataSource dataSource() {
		DataSource mockedDataSrc = Mockito.mock(DataSource.class);
		Connection con = Mockito.mock(Connection.class);
		Statement stmt = Mockito.mock(Statement.class);
		try {

			Mockito.when(mockedDataSrc.getConnection()).then(t -> {
				System.out.println("getconn ");
				return con;
			});
			Mockito.doAnswer(t -> {
				System.out.println("set auto false  ");
				return new DoesNothing();
			}).when(con).setAutoCommit(false);
			Mockito.doAnswer(t -> {
				System.out.println("set auto false  ");
				return new DoesNothing();
			}).when(con).setAutoCommit(true);
			;
			Mockito.doAnswer(t -> {
				System.out.println("commit  ");
				return new DoesNothing();
			}).when(con).commit();
			Mockito.doAnswer(t -> {
				System.out.println("rollback  ");
				return new DoesNothing();
			}).when(con).rollback();
			Mockito.doAnswer(t -> {
				System.out.println("close con  ");
				return new DoesNothing();
			}).when(con).close();
			Mockito.when(con.createStatement()).thenReturn(stmt);
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return mockedDataSrc;
	}

	 @Bean
	 public JdbcTemplate jdbcTemplate() {
	 return new JdbcTemplate(dataSource());
	 }
}
