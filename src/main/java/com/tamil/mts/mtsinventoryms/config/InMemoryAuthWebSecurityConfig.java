/*
 * Created on 29-Nov-2020
 * Created by murugan
 * Copyright � 2020 MTS [murugan425]. All Rights Reserved.
 */
package com.tamil.mts.mtsinventoryms.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

/**
 * @author murugan
 *
 */
@Configuration
@EnableWebSecurity
public class InMemoryAuthWebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Value("${mts.security.salt}")
	private String salt;

	@Bean
	PasswordEncoder pswdEncoder() {
		return new StandardPasswordEncoder(salt);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests(authorize -> {
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

		//Using the encoded SSHA encoded pswd in the security config, so that the actual pswd is hidden.
		auth.inMemoryAuthentication().withUser("testadmin").password("e4d72ecdf020c87e9efb89721a7d7f91d444f6c43b82d425de41dfafe0993435b794c6d8eeee6447").roles("ADMIN").and()
				.withUser("testuser").password("aab1bfa381e85f7048589d8d568c68ea0bb544abe547548c69a8be8364dc67c023ab279c5a70f7af").roles("USER").and().withUser("testcustomer")
				.password("69bbf9b00ebc6b2bb56da3678de3b4d3b30f7dfc55cc4ed6ecafdad45f9bfc21d5bcf4cb86a8eb79").roles("CUSTOMER");

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
