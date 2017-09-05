package test;

import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;
import org.springframework.test.context.support.AbstractTestExecutionListener;

public class MyTestListener extends AbstractTestExecutionListener {

	@Override
	public void beforeTestClass(TestContext testContext) throws Exception {
		super.beforeTestClass(testContext);
		System.out.println("beforeTestClass ..");
		testContext.getApplicationContext().getBeansOfType(TestExecutionListener.class).forEach((name,bean)->System.out.println(name+" :"+bean));;
	}

	@Override
	public void prepareTestInstance(TestContext testContext) throws Exception {
		testContext.getApplicationContext().getBeansOfType(TestExecutionListener.class).forEach((name,bean)->System.out.println(name+" :"+bean));;
		super.prepareTestInstance(testContext);
		System.out.println("prepareTestInstance .."+testContext.getTestMethod()+" "+ testContext.getTestInstance());
	}

	@Override
	public void beforeTestMethod(TestContext testContext) throws Exception {
		 
		super.beforeTestMethod(testContext);
		
		System.out.println("beforeTestMethod .."+testContext.getTestMethod()+" "+ testContext.getTestInstance());
	}

	@Override
	public void afterTestMethod(TestContext testContext) throws Exception {
		 
		super.afterTestMethod(testContext);
		System.out.println("afterTestMethod .."+testContext.getTestMethod()+" "+ testContext.getTestInstance());
	}

	@Override
	public void afterTestClass(TestContext testContext) throws Exception {
		 
		super.afterTestClass(testContext);
		System.out.println("afterTestClass ..");
	}
   
}