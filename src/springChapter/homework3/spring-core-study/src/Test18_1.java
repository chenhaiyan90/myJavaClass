import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import tx.MyLessionService4;
import util.LogUtil;

public class Test18_1 {

	public static void main(String[] args) throws Exception {
		LogUtil.initLogger();
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Tx1JavaConfig4.class);
		MyLessionService4 lessionSvc = ctx.getBean(MyLessionService4.class);
		System.out.println("lessionSvc "+lessionSvc.getClass().getName());
		try
		{
		lessionSvc.autoAddSessions();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		try
		{
			lessionSvc.addNewLession("leader.us.1111", 9999);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		try
		{
			lessionSvc.addNewLession("java.1111", 9999);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		 
		ctx.close();
	}

}
