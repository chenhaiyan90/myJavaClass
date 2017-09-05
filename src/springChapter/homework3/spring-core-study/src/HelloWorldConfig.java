import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class HelloWorldConfig {

	@Bean
	HelloWorldBean helloBean()
	{
		HelloWorldBean thebean= new HelloWorldBean();
		thebean.setMyName("Best one of Leader.us course");
		return thebean;
	}
}
