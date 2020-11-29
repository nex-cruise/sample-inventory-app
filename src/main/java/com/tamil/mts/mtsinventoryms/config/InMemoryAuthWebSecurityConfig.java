/*
 * Created on 29-Nov-2020
 * Created by murugan
 * Copyright � 2020 MTS [murugan425]. All Rights Reserved.
 */
package com.tamil.mts.mtsinventoryms.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author murugan
 *
 */
@Configuration
@EnableWebSecurity
public class InMemoryAuthWebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	PasswordEncoder pswdEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests(authorize -> {
			authorize.antMatchers("/", "/h2-console/**").permitAll();
			// Providing access to contact resources GET without authentication.
			// .antMatchers(HttpMethod.GET, "/mts/api/v1/contact/*").permitAll();
		}).authorizeRequests().anyRequest().authenticated().and().formLogin().and().httpBasic();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// noop - password encoder

//		auth.inMemoryAuthentication().withUser("testadmin").password("{noop}testpswd").roles("ADMIN").and()
//				.withUser("testuser").password("{noop}testpswd").roles("USER").and()
//				.withUser("testcustomer").password("{noop}testpswd").roles("CUSTOMER");

		auth.inMemoryAuthentication().withUser("testadmin").password("testpswd").roles("ADMIN").and()
				.withUser("testuser").password("testpswd").roles("USER").and().withUser("testcustomer")
				.password("testpswd").roles("CUSTOMER");

//		for(UserDetails userDetails: buildUserDetails()) {
//			auth.inMemoryAuthentication().withUser(userDetails);
//		}

	}

	@SuppressWarnings("deprecation")
	private List<UserDetails> buildUserDetails() {
		List<UserDetails> userDetails = new ArrayList<UserDetails>();
		UserDetails adminUser = User.withDefaultPasswordEncoder().username("testadmin").password("testpswd")
				.roles("ADMIN").build();
		UserDetails normalUser = User.withDefaultPasswordEncoder().username("testuser").password("testpswd")
				.roles("USER").build();
		UserDetails customer = User.withDefaultPasswordEncoder().username("testcustomer").password("testpswd")
				.roles("CUSTOMER").build();
		Collections.addAll(userDetails, adminUser, normalUser, customer);
		return userDetails;
	}

}
