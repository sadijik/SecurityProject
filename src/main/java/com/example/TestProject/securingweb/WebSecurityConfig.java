package com.example.TestProject.securingweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	final UserDetailsService userDetailsService;


	@Autowired
	public WebSecurityConfig(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	////
	@Bean
	public AuthenticationSuccessHandler myAuthenticationSuccessHandler() {
		return new MySimpleUrlAuthenticationSuccessHandler();
	}


	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

/*	@Bean
	public DaoAuthenticationProvider authProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(this.userDetailsService);
		authProvider.setPasswordEncoder(getPasswordEncoder());
		return authProvider;
	}*/
//
//	@Bean
//	public PasswordEncoder passwordEncoder(){
//      return new BCryptPasswordEncoder();}
//
/*	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	auth.authenticationProvider(authProvider());
	}*/


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.authorizeRequests()
				.antMatchers("/anonymous*").anonymous()
				.antMatchers("/login*").permitAll()
				.anyRequest().authenticated()

				.and()
				.formLogin()
				.loginPage("/login")
				.loginProcessingUrl("/login")
				.successHandler(myAuthenticationSuccessHandler())
				.failureUrl("/login")

				.and()
				.logout().deleteCookies("JSESSIONID")

				.and()
				.rememberMe().key("uniqueAndSecret").tokenValiditySeconds(86400)

				.and()
				.csrf().disable()
				.logout()
				.logoutSuccessUrl("/")
				.permitAll();
			/*	.authorizeRequests()
				.antMatchers(*//*"/main**",*//*"/admin/**").hasRole("ADMIN")
				.antMatchers("/main/**"*//*"/user"*//*).hasRole("USER")
				.antMatchers("/", "/login").permitAll()
				.anyRequest().authenticated()
				.and()
				.formLogin()
				.loginPage("/login")
				.permitAll()
				.and()
				.logout()
				.logoutSuccessUrl("/")
				.permitAll();*/
	}

	///
	@Configuration
	@Order(1)
	public static class App1ConfigurationAdapter extends WebSecurityConfigurerAdapter {
		public App1ConfigurationAdapter() {
			super();
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
					.antMatcher("/admin/**")
					.authorizeRequests()
					.anyRequest()
					.hasRole("ADMIN")

					.and()
					.formLogin()
					.loginPage("/loginAdmin")
					.loginProcessingUrl("/admin_login")
					.failureUrl("/loginAdmin?error=loginError")
					.defaultSuccessUrl("/adminPage")

					.and()
					.logout()
					.logoutUrl("/admin_logout")
					.logoutSuccessUrl("/protectedLinks")
					.deleteCookies("JSESSIONID")

					.and()
					.exceptionHandling()
					.accessDeniedPage("/403")

					.and()
					.csrf().disable();
		}
	}

	@Configuration
	@Order(2)
	public static class App2ConfigurationAdapter extends WebSecurityConfigurerAdapter {

		public App2ConfigurationAdapter() {
			super();
		}

		protected void configure(HttpSecurity http) throws Exception {
			http.antMatcher("/user/**")
					.authorizeRequests()
					.anyRequest()
					.hasRole("USER")

					.and()
					.formLogin()
					.loginPage("/loginUser")
					.loginProcessingUrl("/user_login")
					.failureUrl("/loginUser?error=loginError")
					.defaultSuccessUrl("/userPage")

					.and()
					.logout()
					.logoutUrl("/user_logout")
					.logoutSuccessUrl("/protectedLinks")
					.deleteCookies("JSESSIONID")

					.and()
					.exceptionHandling()
					.accessDeniedPage("/403")

					.and()
					.csrf().disable();
		}
	}
}
