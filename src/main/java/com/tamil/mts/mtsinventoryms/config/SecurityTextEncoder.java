/*
 * Created on 29-Nov-2020
 * Created by murugan
 * Copyright � 2020 MTS [murugan425]. All Rights Reserved.
 */
package com.tamil.mts.mtsinventoryms.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

/**
 * @author murugan
 *
 */
@Component("textEncoder")
public class SecurityTextEncoder {

	@Value("${mts.security.salt}")
	private String salt;
	
	public String md5Hashing(String text) {
		return DigestUtils.md5DigestAsHex(text.getBytes());
	}
	
	public String md5HashingWithSalt(String text) {
		return DigestUtils.md5DigestAsHex((text+salt).getBytes());
	}
	
	public String md5HashingWithSalt(String text, String salt) {
		return DigestUtils.md5DigestAsHex((text+salt).getBytes());
	}
}
