import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.NoRollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import tx.MyCustTXException;

@Configuration
@ComponentScan("tx")
@EnableAspectJAutoProxy
public class Tx1JavaConfig4 {

	@Bean
	public TransactionInterceptor transactionInterceptor() {
	    return new TransactionInterceptor(getTransactionManager(), transactionAttributeSource());
	}

	@Bean
	public NameMatchTransactionAttributeSource transactionAttributeSource() {
	    NameMatchTransactionAttributeSource tas = new NameMatchTransactionAttributeSource();
	    
	    RuleBasedTransactionAttribute readOnlyTx = new RuleBasedTransactionAttribute();
	    readOnlyTx.setReadOnly(true);

	    RuleBasedTransactionAttribute requredTx = new RuleBasedTransactionAttribute(TransactionDefinition.PROPAGATION_REQUIRED, Collections.singletonList(new NoRollbackRuleAttribute(MyCustTXException.class)));

	    Map<String, TransactionAttribute> txMaps = new HashMap<>();
	    txMaps.put("get*", readOnlyTx);
	    txMaps.put("add*", requredTx);
	    tas.setNameMap(txMaps);
	    return tas;
	}
	
	@Bean
	public AspectJExpressionPointcutAdvisor transactionAdvisor() {
	    AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
	    advisor.setAdvice(transactionInterceptor());
	    advisor.setExpression("execution(* tx.*.*(..)) ");
	    return advisor;
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
