import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.ResolvableType;

import leader.service.AbstractService;
import leader.service.UserService;

public class Main11 {

	public static void main(String[] args) throws InterruptedException {
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext("applicationContext4.xml");
		UserService userSvc=ctx.getBean(UserService.class);
		 System.out.println("dao class "+userSvc.getDao().getClass());
		 
		ResolvableType resolvableType1 = ResolvableType.forClass(UserService.class);  
		System.out.println("type is " + resolvableType1.as(AbstractService.class).getGeneric(0).resolve());
		
		ParameterizedType parameterizedType =   
			    (ParameterizedType) UserService.class.getGenericSuperclass();  
			Type genericType = parameterizedType.getActualTypeArguments()[0];  
			System.out.println("type is " + genericType);
		ctx.registerShutdownHook();

	}
}