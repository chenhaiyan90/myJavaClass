import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import leader.App;
import leader.domain.TBUser;
import leader.service.UserService;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
@Transactional
public class TestUserService {

	@Autowired
	private UserService userSrv;
	@Test 
	public void testInsertUser()
	{
		TBUser user =new TBUser();
		user.setUsername("test ccc");
		user.setPassword("11q2we3");
		userSrv.createUser(user);
	}

	@Test
	public void testQueryusers(){
		userSrv.getAllUsers().stream().forEach(obj->System.out.println(obj));
		System.out.println(123);
	}
	@Test 
	public void testSelectUsersByParams()
	{
		
		 
		userSrv.queryUserByParams("leader", true);
	}
	@Test 
	public void testSelectUsers()
	{
		
		 List<String> userIds=Arrays.asList("leader","test 11","ccc");
		userSrv.queryUsers(userIds).forEach(u-> System.out.println(u));
	}
}
