package leader;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Iterator;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

import javax.sql.DataSource;

@SpringBootApplication(scanBasePackages = "leader.**")
@MapperScan("leader.mapping")
@EnableRedisHttpSession
@EnableWebSocket  
public class App extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		 
		return application.sources(App.class);
	}

	public static void main(String[] args) throws Exception {
		Enumeration<URL> resouces;
		try {
			resouces = Thread.currentThread().getContextClassLoader().getResources("leader");
			while(resouces.hasMoreElements())
			{
				System.out.println("$$$$$$$$$"+resouces.nextElement());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ConfigurableApplicationContext ctx = SpringApplication.run(App.class, args);
		/* Iterator<String> itors = ctx.getBeanFactory().getBeanNamesIterator();
		 while (itors.hasNext()) {
		 System.out.println(itors.next());
		 }*/
//		AbstractJsonpResponseBodyAdvice
	}
	 @Bean
	 @ConfigurationProperties(prefix = "spring.datasource")
	 public DataSource primaryDataSource() {
	 System.out.println("-------------------- DataSource init ---------------------");
	 return DataSourceBuilder.create().build();
	 }
}