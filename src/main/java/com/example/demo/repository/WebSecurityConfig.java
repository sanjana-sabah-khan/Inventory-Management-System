package com.example.demo.repository;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.demo.service.CustomRegDetailsService;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	
	@Autowired
	private DataSource dataSource;
	
	
	@Bean
	public UserDetailsService regDetailsService () {
		
		return new CustomRegDetailsService ();
	}
	
	//encrypts password
	@Bean
	public BCryptPasswordEncoder passwordEncoder () {
		
		return new BCryptPasswordEncoder();
	}
	
	//checks data from database for authentication of login info
	@Bean
	public DaoAuthenticationProvider authenticationProvider () {
		
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		
		authProvider.setUserDetailsService ( regDetailsService() );
		authProvider.setPasswordEncoder ( passwordEncoder() );
		
		return authProvider;
	}


	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.authenticationProvider ( authenticationProvider() );
	}

	//manages accessibility of pages on web
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		 http.csrf().disable()
 			 .authorizeRequests()
		     .antMatchers("/").authenticated()
		     .antMatchers("/brandOpen").authenticated()
		     .antMatchers("/storesOpen").authenticated()
		     .antMatchers("/productOpen").authenticated()
		     .antMatchers("/orderOpen").authenticated()
		     .antMatchers("/customerOpen").authenticated()
	         .anyRequest().permitAll()
	         .and()
	         .formLogin()
	         	.loginPage("/login")
	         	.usernameParameter("admin_email")
	         	.passwordParameter("admin_password")
	         	.permitAll()
         	.and()
         	.logout().permitAll();
	}
		

}
