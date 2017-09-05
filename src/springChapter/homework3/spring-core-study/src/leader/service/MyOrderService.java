package leader.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import leader.bean.MyServiceLocalStorage;

@Component
public class MyOrderService {
	@Autowired
	private MyServiceLocalStorage localStorage;

	public MyServiceLocalStorage getLocalStorage() {
		return localStorage;
	}

	 
	
	
}
