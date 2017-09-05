import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import tx.MyLessionService3;
import util.LogUtil;

public class Test17 {

	public static void main(String[] args) throws Exception {
		LogUtil.initLogger();
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Tx1JavaConfig2.class);
		MyLessionService3 lessionSvc = ctx.getBean(MyLessionService3.class);
		System.out.println("lessionSvc "+lessionSvc.getClass().getName());
		lessionSvc.otherCall();
		ctx.close();
		
	}

}
