import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import tx.MyLessionService;
import util.LogUtil;
import util.ThreadLocalUtil;

public class Test15 {

	public static void main(String[] args) throws SQLException, SecurityException, IOException {
		//System.setProperty("java.util.logging.config.file", "jdk-log.properties");
		LogUtil.initLogger();
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Tx1JavaConfig.class);
		PlatformTransactionManager txMan=ctx.getBean(PlatformTransactionManager.class);
		
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();  
	    def.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);  
	    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);  
	    def.setTimeout(300);
	    TransactionStatus firstTXstatus = txMan.getTransaction(def);  
	
		MyLessionService lessionSvc =  ctx.getBean(MyLessionService.class);
		lessionSvc.addNewLession("java one", 10000);
		//second transaction
		System.out.println("start new transaction ");
		def = new DefaultTransactionDefinition();  
	    def.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);  
	    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_NESTED);  
	    def.setTimeout(300);
	    TransactionStatus secondStatus = txMan.getTransaction(def); 
	    try
	    {
		lessionSvc.queryLessions();
		
		if(true)
		{
			throw new RuntimeException("failed trans");
		}
		txMan.commit(secondStatus);
	    }catch(Exception e)
	    {
	    	
	    	e.printStackTrace();
	    	txMan.rollback(secondStatus);
	    }
		txMan.commit(firstTXstatus);
		ctx.close();
	}

}
