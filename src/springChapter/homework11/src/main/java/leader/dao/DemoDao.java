package leader.dao;

import org.springframework.stereotype.Component;

@Component
public class DemoDao {

	public boolean hasAuthority(String userName,Object theObj)
	{
		return true;
	}
}
