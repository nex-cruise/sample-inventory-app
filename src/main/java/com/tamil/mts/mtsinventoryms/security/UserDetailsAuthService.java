/*
 * Created on 02-Dec-2020
 * Created by murugan
 * Copyright � 2020 MTS [murugan425]. All Rights Reserved.
 */
package com.tamil.mts.mtsinventoryms.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tamil.mts.mtsinventoryms.domain.security.Authority;
import com.tamil.mts.mtsinventoryms.domain.security.MTSUser;
import com.tamil.mts.mtsinventoryms.repositories.security.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author murugan
 *
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsAuthService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		MTSUser mtsUser = userRepository.findUserByUsername(username).orElseThrow(() -> {
			log.debug("Username: " + username + " not found in MTS Authorization Repository");
			return new UsernameNotFoundException(
					"Username: " + username + " not found in MTS Authorization Repository");
		});
		log.debug("Loading User details from MTS Auth Repository for username: " + username + ". MTS User Details: "
				+ mtsUser.toString());
		UserDetails userDetails = User.builder().username(mtsUser.getUsername()).password(mtsUser.getPassword())
				.accountExpired(!mtsUser.isAccountNonExpired()).accountLocked(!mtsUser.isAccountNonLocked())
				.credentialsExpired(!mtsUser.isCredentialsNonExpired())
				.authorities(getGrantedAuthorities(mtsUser.getAuthorities())).build();
		log.debug(userDetails.toString());
		return userDetails;
	}

	/**
	 * Convert the MTS User Authority to Spring Authority class object
	 * 
	 * @param Set<Authority> authorities
	 * @return Set<SimpleGrantedAuthority> granterAuthorities
	 */
	private Collection<? extends GrantedAuthority> getGrantedAuthorities(Set<Authority> authorities) {
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		if (!authorities.isEmpty()) {
			grantedAuthorities.addAll(authorities.stream().map(Authority::getRole).map(SimpleGrantedAuthority::new)
					.collect(Collectors.toSet()));
		}
		return grantedAuthorities;
	}
}
