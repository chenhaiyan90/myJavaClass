package test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestExecutionListeners.MergeMode;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import leader.service.HelloWorldBean3;


@RunWith(SpringRunner.class) 
@ContextConfiguration("classpath:applicationContext2.xml") 
@TestExecutionListeners(listeners = MyTestListener.class,mergeMode=MergeMode.MERGE_WITH_DEFAULTS)
public class Test3 {

	
	public Test3()
	{
		System.out.println("created Test3 Object "+this);
	}
	@Autowired
	private HelloWorldBean3 hellBean3;
	
	@Test
	@Sql("insert-test-data.sql")
	public void test1()
	{
		hellBean3.hello();
	}
	
	@Test
	public void test2()
	{
		 
	}
	
	@Test
	public void test3()
	{
		 
	}
}
