/*
 * Created on 29-Nov-2020
 * Created by murugan
 * Copyright � 2020 MTS [murugan425]. All Rights Reserved.
 */
package com.tamil.mts.mtsinventoryms.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import lombok.extern.slf4j.Slf4j;

/**
 * @author murugan
 *
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@WebMvcTest(SecurityTextEncoder.class)
@ActiveProfiles("test")
public class SecurityTextEncoderTest {

	@Autowired
	public SecurityTextEncoder textEncoder;
	
	@Test
	public void logMd5EncodersHashValues() {
		//Log multiple times to make sure the same hash value is returned
		log.info(textEncoder.md5Hashing("murugan"));
		log.info(textEncoder.md5Hashing("murugan"));
		log.info(textEncoder.md5HashingWithSalt("murugan"));
		log.info(textEncoder.md5HashingWithSalt("murugan"));
		log.info(textEncoder.md5HashingWithSalt("murugan", "testsalt"));
		log.info(textEncoder.md5HashingWithSalt("murugan", "testsalt"));
		assertTrue(true);
	}
	
	@Test
	public void testMd5Encoders() {
		assertEquals(textEncoder.md5Hashing("murugan"), textEncoder.md5Hashing("murugan"),
				"md5Hashing - encoder test failed");
		assertEquals(textEncoder.md5HashingWithSalt("murugan"), textEncoder.md5HashingWithSalt("murugan"),
				"md5Hashing - default Salt test failed");
		assertEquals(textEncoder.md5HashingWithSalt("murugan", "testsalt"),
				textEncoder.md5HashingWithSalt("murugan", "testsalt"), "md5Hashing - custom Salt test failed");
	}
	

	@Test
	public void logNoOpEncoderValues() {
		log.info(textEncoder.noOpEncoder("murugan"));
		log.info(textEncoder.noOpEncoder("murugan"));
		assertTrue(true);
	}

	@Test
	public void logLdapEncoderHashValues() {
		log.info(textEncoder.ldapHashing("testpswd"));
		log.info(textEncoder.ldapHashing("testpswd"));
		String encodedTxt = textEncoder.ldapHashing("murugan");
		assertTrue(textEncoder.ldapPasswordValid("murugan", encodedTxt));
	}
}
