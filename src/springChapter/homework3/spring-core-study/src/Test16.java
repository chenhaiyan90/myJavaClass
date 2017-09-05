import java.io.IOException;
import java.sql.SQLException;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import tx.MyLessionService;
import util.LogUtil;

public class Test16 {

	public static void main(String[] args) throws SQLException, SecurityException, IOException {
		LogUtil.initLogger();
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Tx1JavaConfig.class);
		PlatformTransactionManager txMan = ctx.getBean(PlatformTransactionManager.class);
		MyLessionService lessionSvc = ctx.getBean(MyLessionService.class);
		TransactionTemplate txTemp = new TransactionTemplate(txMan);
		txTemp.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
		txTemp.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		txTemp.setTimeout(300);
		txTemp.execute(t -> {
			try {
				lessionSvc.addNewLession("java one", 10000);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		});

		ctx.close();
	}

}
