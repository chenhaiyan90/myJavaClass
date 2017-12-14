package leader.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

//@EnableGlobalMethodSecurity(prePostEnabled = true)
//@Configuration
public class JDBCSecurityConfig2 extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource datasource;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// http.authorizeRequests().antMatchers("/admin/**").authenticated().and().formLogin().and();
		http.authorizeRequests().antMatchers("/admin/**").hasAnyRole("admin", "users").and().formLogin().and()
				.rememberMe().tokenValiditySeconds(31536000).and();
		// http.authorizeRequests().antMatchers("/manager/**").access("@mysecurityRule.check(authentication,request)")
		// .and();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("**/js/**", "**/css/**", "**/images/**", "**/**/favicon.ico");

	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(jdbcUserDetailsManager());
	}

	public UserDetailsManager jdbcUserDetailsManager() throws Exception {
		JdbcUserDetailsManager userMan = new JdbcUserDetailsManager();
		userMan.setDataSource(datasource);
		userMan.setRolePrefix("ROLE_");
		return userMan;
	}

}