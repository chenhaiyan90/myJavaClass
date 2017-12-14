package leader.security;

import java.util.Map.Entry;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

//@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	protected static final String encodeStr = "341231412423423";

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/admin/**").authenticated().and().httpBasic();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("**/js/**", "**/css/**", "**/images/**", "**/**/favicon.ico");

	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(inMemoryUserDetailsManager());
	}

	@SuppressWarnings("rawtypes")
	@Bean
	public InMemoryUserDetailsManager inMemoryUserDetailsManager() throws Exception {
		Properties userPropers = PropertiesLoaderUtils.loadProperties(new ClassPathResource("/users.dat"));
		Properties newUsers = new Properties();
		for (Entry entry : userPropers.entrySet()) {
			String user = (String) entry.getKey();
			String value = (String) entry.getValue();
			String[] valueItems = value.split(",");
			String password = valueItems[0];
			password = PBEUtil.decrypt(password, encodeStr);
			String newValue = password + "," + valueItems[1] + "," + valueItems[2];
			// users.put("user","pass,ROLE_USER,enabled");
			newUsers.setProperty(user, newValue);
		}
		return new InMemoryUserDetailsManager(newUsers);
	}

}