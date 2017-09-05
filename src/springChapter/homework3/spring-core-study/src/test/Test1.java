package test;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class Test1 {
	static ApplicationContext ctx = null;

	enum TestStage {
		test01_DeleteUser, test02_InsertUser
	}

	static TestStage curStage = TestStage.test01_DeleteUser;

	@BeforeClass
	public static void initTest() {
		System.out.println("init test ");
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

	}

	@Before
	public void prepareData() {
		System.out.println("prepared for " + curStage);
		switch (curStage) {
		case test01_DeleteUser: {
			doPrepareInstertUserData();
			break;
		}
		case test02_InsertUser: {
			doPrepareDeleteUserData();
			break;
		}
		default: {
			System.out.println("Warning ,not prepared for " + curStage);
		}
		}
	}

	@After
	public void destroyData() {
		System.out.println("destroy data for " + curStage);
		switch (curStage) {
		case test01_DeleteUser: {
			doDestroyInstertUserData();
			break;
		}
		case test02_InsertUser: {
			doDestroyDeleteUserData();
			break;
		}
		default: {
			System.out.println("Warning ,not destroy data for " + curStage);
		}
		}
	}

	@Test
	public void test02_InsertUser() {
		System.out.println("test    test02InsertUser ");
	}

	@Test
	public void test01_DeleteUser() {
		System.out.println("test    test01DeleteUser ");
		curStage = TestStage.test02_InsertUser;
	}
	
	@Test
	public void test03_UpdateUser()
	{
		try
		{
			//prepare data ....
			//mytest Code
		}finally
		{
			//destroy data ....
		}
		
	}

	private void doPrepareDeleteUserData() {

	}

	private void doPrepareInstertUserData() {

	}

	private void doDestroyDeleteUserData() {
		
	}

	private void doDestroyInstertUserData() {
		

	}
}
