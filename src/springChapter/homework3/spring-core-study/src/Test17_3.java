import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import tx.MyLessionService5;
import util.LogUtil;

public class Test17_3 {

	public static void main(String[] args) throws Exception {
		LogUtil.initLogger();
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Tx1JavaConfig2.class);
		MyLessionService5 lessionSvc = ctx.getBean(MyLessionService5.class);
		System.out.println("lessionSvc "+lessionSvc.getClass().getName());
		try
		{
		lessionSvc.autoAddSessions();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		lessionSvc.addNewLession("leaderus.1111", 9999);
		ctx.close();
		 
	}

}
