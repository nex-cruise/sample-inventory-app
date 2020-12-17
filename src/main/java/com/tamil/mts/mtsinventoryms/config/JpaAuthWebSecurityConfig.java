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
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.tamil.mts.mtsinventoryms.security.RestHeaderAuthFilter;
import com.tamil.mts.mtsinventoryms.security.RestUrlAuthFilter;
import com.tamil.mts.mtsinventoryms.security.SecurityTextEncoderFactories;

import lombok.RequiredArgsConstructor;

/**
 * @author murugan
 *
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class JpaAuthWebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Value("${mts.security.salt}")
	private String salt;

//	private final UserDetailsAuthService userDetailsAuthService;

	@Bean
	PasswordEncoder pswdEncoder() {
		return SecurityTextEncoderFactories.createDelegatingPasswordEncoder(salt);
	}

	public RestHeaderAuthFilter restHeaderAuthFilter(AuthenticationManager authenticationManager) {
		RestHeaderAuthFilter filter = new RestHeaderAuthFilter(new AntPathRequestMatcher("/mts/api/v1/**"));
		filter.setAuthenticationManager(authenticationManager);
		return filter;
	}

	private RestUrlAuthFilter restUrlAuthFilter(AuthenticationManager authenticationManager) {
		RestUrlAuthFilter filter = new RestUrlAuthFilter(new AntPathRequestMatcher("/mts/api/v1/**"));
		filter.setAuthenticationManager(authenticationManager);
		return filter;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();

		http.addFilterBefore(restHeaderAuthFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);

		http.addFilterBefore(restUrlAuthFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);

		http.headers().frameOptions().sameOrigin();

		http.authorizeRequests(authorize -> {
			authorize.antMatchers("/", "/h2-console/**").permitAll();
		}).authorizeRequests().anyRequest().authenticated().and().formLogin().and().httpBasic();
	}

//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(userDetailsAuthService).passwordEncoder(pswdEncoder());
//	}

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
