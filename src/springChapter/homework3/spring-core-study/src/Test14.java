import java.sql.SQLException;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import tx.MyLessionService;
import util.ThreadLocalUtil;

public class Test14 {

	public static void main(String[] args) throws SQLException {

		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Tx1JavaConfig.class);
		PlatformTransactionManager txMan=ctx.getBean(PlatformTransactionManager.class);
		ThreadLocalUtil.dumphreadLocals();
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();  
	    def.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);  
	    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);  
	    def.setTimeout(300);
	    TransactionStatus status = txMan.getTransaction(def);  
	   
	    
	    ThreadLocalUtil.dumphreadLocals();
		MyLessionService lessionSvc =  ctx.getBean(MyLessionService.class);
		ThreadLocalUtil.dumphreadLocals();
		lessionSvc.addNewLession("java one", 10000);
		ThreadLocalUtil.dumphreadLocals();
		lessionSvc.queryLessions();
		ThreadLocalUtil.dumphreadLocals();
		txMan.commit(status);
		ThreadLocalUtil.dumphreadLocals();
		ctx.close();
	}

}
