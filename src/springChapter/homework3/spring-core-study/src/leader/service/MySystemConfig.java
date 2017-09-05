package leader.service;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
@Configuration
@PropertySource(value="classpath:myapp.properties", ignoreResourceNotFound=true)
public class MySystemConfig {
public MySystemConfig()
{
	System.out.println("@Configuration MySystemConfig created " );
}
@Bean HelloWorldBean anotherHelloBean()
{
	return new HelloWorldBean();
}
}
