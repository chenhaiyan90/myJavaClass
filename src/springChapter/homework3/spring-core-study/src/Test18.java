import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import tx.MyLessionService2;
import util.LogUtil;

public class Test18 {

	public static void main(String[] args) throws Exception {
		LogUtil.initLogger();
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Tx1JavaConfig3.class);
		MyLessionService2 lessionSvc = ctx.getBean(MyLessionService2.class);
		System.out.println("lessionSvc "+lessionSvc.getClass().getName());
		lessionSvc.otherCall();
		ctx.close();
		//InfrastructureAdvisorAutoProxyCreator
		//AnnotationAwareAspectJAutoProxyCreator s;
		//BeanFactoryAdvisorRetrievalHelper ss;
		//TransactionInterceptor
		//AnnotationTransactionAspect
		//AnnotationTransactionAspect.aspectOf().setTransactionManager(transactionManager);
	}

}
