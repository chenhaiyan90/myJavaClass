package test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import leader.service.HelloWorldBean3;
@RunWith(SpringRunner.class) 
@ContextConfiguration("classpath:applicationContext2.xml") 
@ActiveProfiles("unit_test")
//@Transactional
public class Test4 {

	@Autowired
	private HelloWorldBean3 hellBean3;
	
	@Test
	@Sql("classpath:test/test.sql")
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
