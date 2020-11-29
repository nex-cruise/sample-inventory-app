/*
 * Created on 29-Nov-2020
 * Created by murugan
 * Copyright � 2020 MTS [murugan425]. All Rights Reserved.
 */
package com.tamil.mts.mtsinventoryms.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder.BCryptVersion;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

/**
 * @author murugan
 *
 */
@SuppressWarnings("deprecation")
public class SecurityTextEncoderFactories {

	public static PasswordEncoder createDelegatingPasswordEncoder(String mtsSaltKey) {
		Map<String, PasswordEncoder> encoders = new HashMap<>();
		encoders.put("bcrypt", new BCryptPasswordEncoder(BCryptVersion.$2Y, 5));
		encoders.put("ldap", new org.springframework.security.crypto.password.LdapShaPasswordEncoder());
		encoders.put("MD5", new org.springframework.security.crypto.password.MessageDigestPasswordEncoder("MD5"));
		encoders.put("noop", org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance());
		encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
		encoders.put("scrypt", new SCryptPasswordEncoder());
		encoders.put("sha256", new StandardPasswordEncoder(mtsSaltKey));
		encoders.put("argon2", new Argon2PasswordEncoder());
		return new DelegatingPasswordEncoder("bcrypt", encoders);
	}

	private SecurityTextEncoderFactories() {
	}
}
