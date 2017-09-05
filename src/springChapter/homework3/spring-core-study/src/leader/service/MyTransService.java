package leader.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class MyTransService {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Transactional
	public void  doTransBusiness()
	{
		jdbcTemplate.execute("insert into mydata ");
		System.out.println(" MyTransService in transaction fake method ");
	}
}
