package com.cropaccounting.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.UserDetailsManagerConfigurer.UserDetailsBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.cropaccounting.config.SpringSecurityConfigUserList.User;

@Order(1)
@EnableWebSecurity
@ComponentScan("com.cropaccounting")
@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Autowired
	private SpringSecurityConfigUserList userDetail;
	
	@Autowired
	private AccessDeniedHandler accessDeniedHandler;

	@SuppressWarnings("rawtypes")
	private UserDetailsBuilder udb;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//http.httpBasic().and().authorizeRequests().anyRequest().fullyAuthenticated().and().csrf().disable();
		/*http.authorizeRequests()
			.antMatchers("/", "/home").access("hasRole('USER')")
			.antMatchers("/admin/**").access("hasRole('ADMIN')")
			.antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")
			.and().formLogin().loginPage("/login").successHandler(customSuccessHandler)
			.usernameParameter("ssoId").passwordParameter("password")
			.and().csrf()
			.and().exceptionHandling()
			.accessDeniedPage("/Access_Denied");*/
		http.csrf().disable().authorizeRequests()
			.antMatchers("/", "/about", "/signup", "/lib/**", "/css/**", "/img/**", "/js/**", "/saml/**").permitAll()
			//.antMatchers("/cropaccounting/**").hasRole("ADMIN")
			//.antMatchers("/cropmanagement/**").access("hasRole('ADMIN')")
			.antMatchers("/cropmanagement/**").access("hasAnyRole('ADMIN','EO')")
			.antMatchers("/app/**").access("hasAnyRole('USER')")
			.antMatchers("/reports/**").access("hasAnyRole('ADMIN','EO')")
			.antMatchers("/eo/**").access("hasAnyRole('EO')")
			.antMatchers("/webjars/**").permitAll()
			.anyRequest().authenticated()
        .and()
        .formLogin()
			.loginPage("/login")
			.permitAll()
			.and()
        .logout()
			.permitAll()
			.and()
        .exceptionHandling().accessDeniedHandler(accessDeniedHandler);
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder> abc = auth.inMemoryAuthentication()
				.passwordEncoder(passwordEncoder());

		for (int i = 0; i < userDetail.getUsers().size(); i++) {
			User user = userDetail.getUsers().get(i);
			if (i == 0) {
				udb = abc.withUser(user.getCode()).password(user.getPassword()).roles(user.getRoles());
			} else {
				udb = udb.and().withUser(user.getCode()).password(user.getPassword()).roles(user.getRoles());
			}
		}
	}

	/**
	 * passwords will be kept in the application.yml in encrypted format this method
	 * will return a encoder with password strength 10, to decrypt and match.
	 * 
	 * @return
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
}
