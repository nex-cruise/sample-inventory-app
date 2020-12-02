/*
 * Created on 02-Dec-2020
 * Created by murugan
 * Copyright � 2020 MTS [murugan425]. All Rights Reserved.
 */
package com.tamil.mts.mtsinventoryms.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.tamil.mts.mtsinventoryms.domain.security.Authority;
import com.tamil.mts.mtsinventoryms.domain.security.MTSUser;
import com.tamil.mts.mtsinventoryms.repositories.security.AuthorityRepository;
import com.tamil.mts.mtsinventoryms.repositories.security.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author murugan
 *
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UserBootstrap implements CommandLineRunner {

	private final UserRepository userRepository;

	private final AuthorityRepository authorityRepository;

	private final PasswordEncoder passwordEncoder;

	@Override
	public void run(String... args) throws Exception {
		loadAuthorizedUsers();
	}

	private void loadAuthorizedUsers() {
		log.info(this.getClass().getSimpleName() + ": loadAuthorities()");
		Authority adminRole = authorityRepository.save(Authority.builder().role("ADMIN").build());
		Authority userRole = authorityRepository.save(Authority.builder().role("USER").build());
		Authority customerRole = authorityRepository.save(Authority.builder().role("CUSTOMER").build());
		log.info(String.format("Total Authorities Loaded : %d", authorityRepository.count()));

		log.info(this.getClass().getSimpleName() + ": loadUsers()");
		MTSUser adminUser = MTSUser.builder().username("testadmin").password(passwordEncoder.encode("testpswd"))
				.authority(adminRole).build();
		MTSUser testUser = MTSUser.builder().username("testuser").password(passwordEncoder.encode("testpswd"))
				.authority(userRole).build();
		MTSUser customerUser = MTSUser.builder().username("testcustomer").password(passwordEncoder.encode("testpswd"))
				.authority(customerRole).build();
		userRepository.save(adminUser);
		userRepository.save(testUser);
		userRepository.save(customerUser);
		log.info("Total Users Loaded : {}", userRepository.count());
	}
}
