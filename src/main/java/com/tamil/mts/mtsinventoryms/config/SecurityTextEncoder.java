/*
 * Created on 29-Nov-2020
 * Created by murugan
 * Copyright � 2020 MTS [murugan425]. All Rights Reserved.
 */
package com.tamil.mts.mtsinventoryms.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder.BCryptVersion;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

/**
 * @author murugan
 *
 */
@SuppressWarnings("deprecation")
@Component("textEncoder")
public class SecurityTextEncoder {

	@Value("${mts.security.salt}")
	private String salt;

	public String md5Hashing(String text) {
		return DigestUtils.md5DigestAsHex(text.getBytes());
	}

	public String md5HashingWithSalt(String text) {
		return DigestUtils.md5DigestAsHex((text + salt).getBytes());
	}

	public String md5HashingWithSalt(String text, String salt) {
		return DigestUtils.md5DigestAsHex((text + salt).getBytes());
	}

	
	// Not recommended, doesn't do any manipulation on the given text.
	// Hence the name NoOp.
	// This PasswordEncoder is provided for legacy and testing purposes only.
	public String noOpEncoder(String text) {
		PasswordEncoder noOpEncoder = NoOpPasswordEncoder.getInstance();
		return noOpEncoder.encode(text);
	}
	
	public String ldapHashing(String text) {
		PasswordEncoder ldapHashing = new LdapShaPasswordEncoder();
		return ldapHashing.encode(text);
	}
	
	public Boolean ldapPasswordValid(String text, String sshaEncodedText) {
		PasswordEncoder ldapHashing = new LdapShaPasswordEncoder();
		return ldapHashing.matches(text, sshaEncodedText);
	}
	
	public String sha256Hashing(String text) {
		PasswordEncoder sha256Hashing = new StandardPasswordEncoder(salt);
		return sha256Hashing.encode(text);
	}
	
	public Boolean sha256PasswordValid(String text, String shaEncodedText) {
		PasswordEncoder sha256Hashing = new StandardPasswordEncoder(salt);
		return sha256Hashing.matches(text, shaEncodedText);
	}
	
	public String bCryptHashing(String text) {
		PasswordEncoder bcryptHashing = new BCryptPasswordEncoder(BCryptVersion.$2Y, 5);
		return bcryptHashing.encode(text);
	}
	
	public Boolean bCryptPasswordValid(String text, String shaEncodedText) {
		PasswordEncoder bcryptHashing = new BCryptPasswordEncoder(BCryptVersion.$2Y, 5);
		return bcryptHashing.matches(text, shaEncodedText);
	}
}
