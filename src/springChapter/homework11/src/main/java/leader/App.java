package leader;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(scanBasePackages = "leader.**")
@MapperScan("leader.mapping")

public class App extends SpringBootServletInitializer {


	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		// SavedRequestAwareAuthenticationSuccessHandler ss;
		// PriorityQueue
		return application.sources(App.class);
	}

	

	public static void main(String[] args) throws Exception {
		// DenyAllPermissionEvaluator
		ConfigurableApplicationContext ctx = SpringApplication.run(App.class, args);
//		Iterator<String> itors = ctx.getBeanFactory().getBeanNamesIterator();
//		while (itors.hasNext()) {
//
//			// FilterChainProxy
//			String name = itors.next();
//			System.out.println(name + " " + (!name.contains(".") ? ctx.getBeanFactory().getBean(name) : ""));
//		}

	}
	// Iterator<String> itors = ctx.getBeanFactory().getBeanNamesIterator();
	// while (itors.hasNext()) {
	// System.out.println(itors.next());
	// }
	// @Bean
	// @ConfigurationProperties(prefix = "spring.datasource")
	// public DataSource primaryDataSource() {
	// System.out.println("-------------------- DataSource init
	// ---------------------");
	// return DataSourceBuilder.create().build();
	// }
}