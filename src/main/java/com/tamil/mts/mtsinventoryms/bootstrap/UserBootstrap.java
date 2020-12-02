/*
 * Created on 02-Dec-2020
 * Created by murugan
 * Copyright � 2020 MTS [murugan425]. All Rights Reserved.
 */
package com.tamil.mts.mtsinventoryms.bootstrap;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

//		Set<Authority> authorities = new HashSet<>();
//		Collections.addAll(authorities, adminRole, userRole, customerRole);
//		List<String> roles = authorities.stream().map(Authority::getRole).collect(Collectors.toList());
//		log.info("List of MTS Authority Roles: " + roles.toString());
		
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
