package leader.service;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import leader.bean.MyServiceLocalStorage;

@Component
@Lazy
public class MyServiceLocalStorageFactory implements FactoryBean<MyServiceLocalStorage>{

	public MyServiceLocalStorageFactory()
	{
		System.out.println("MyServiceLocalStorageFactory created");
	}
	public MyServiceLocalStorage getObject() throws Exception {
		MyServiceLocalStorage token=new MyServiceLocalStorage();
		System.out.println("created  MyServiceLocalStorage "+token);
		return token;
	}

	public Class<?> getObjectType() {
		return MyServiceLocalStorage.class;
	}

	public boolean isSingleton() {

		return false;
	}

}
