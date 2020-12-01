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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.tamil.mts.mtsinventoryms.security.RestHeaderAuthFilter;
import com.tamil.mts.mtsinventoryms.security.SecurityTextEncoderFactories;

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
		return SecurityTextEncoderFactories.createDelegatingPasswordEncoder(salt);
	}

	public RestHeaderAuthFilter restHeaderAuthFilter(AuthenticationManager authenticationManager) {
		RestHeaderAuthFilter filter = new RestHeaderAuthFilter(new AntPathRequestMatcher("/mts/api/v1/**"));
		filter.setAuthenticationManager(authenticationManager);
		return filter;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.addFilterBefore(restHeaderAuthFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class)
				.csrf().disable();

		http.csrf().disable().authorizeRequests(authorize -> {
			authorize.antMatchers("/", "/h2-console/**").permitAll();
//				Providing access to contact resources GET without authentication.
//				.antMatchers(HttpMethod.GET, "/mts/api/v1/contact/*").permitAll();
		}).authorizeRequests().anyRequest().authenticated().and().formLogin().and().httpBasic();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		noop - password encoder

//		auth.inMemoryAuthentication().withUser("testadmin").password("{noop}testpswd").roles("ADMIN").and()
//				.withUser("testuser").password("{noop}testpswd").roles("USER").and()
//				.withUser("testcustomer").password("{noop}testpswd").roles("CUSTOMER");

//		Using the encoded SSHA encoded pswd in the security config, so that the actual pswd is hidden.
		auth.inMemoryAuthentication().withUser("testadmin")
				.password("{bcrypt}$2y$05$1kTk2bUWCYbXOCB4Lcb5uuJfY7XB/XIxhfof5ElbObdiiQM.JcxCm").roles("ADMIN").and()
				.withUser("testuser").password("{ldap}{SSHA}iSljD/RdAnMFw3Pdv3+XC5mj2JABFNlzPjldSQ==").roles("USER")
				.and().withUser("testcustomer")
				.password("{sha256}11e8fef4319c9687504cf9bc1066be5aa7c1e13c38718963e5b9081ea454be14f4f38a5aa1c045e9")
				.roles("CUSTOMER");

//		for(UserDetails userDetails: buildUserDetails()) {
//			auth.inMemoryAuthentication().withUser(userDetails);
//		}

	}

	@SuppressWarnings({ "deprecation", "unused" })
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
