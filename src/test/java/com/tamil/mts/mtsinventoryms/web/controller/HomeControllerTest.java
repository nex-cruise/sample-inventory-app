/*
 * Created on 22-Nov-2020
 * Created by murugan
 * Copyright � 2020 MTS [murugan425]. All Rights Reserved.
 */
package com.tamil.mts.mtsinventoryms.web.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;

/**
 * @author murugan
 *
 */
@WebMvcTest(HomeController.class)
public class HomeControllerTest extends BaseSecurityIT {

	// This gives a mock user for the test purpose, it doesn't goes through the
	// actual authentication configured for the application.
	@WithMockUser("testuser")
	@Test
	void testHomeWithedMockedUser() throws Exception {
		mockMvc.perform(get("/home")).andExpect(status().isOk());
	}

	// Providing no user for test method fails as spring security is configured.
	@Test
	void testHomeNoUser() throws Exception {
		mockMvc.perform(get("/home")).andExpect(status().is4xxClientError());
	}

	// This test goes through the authentication configuration to validate the given
	// user credentials
	@Test
	void testHomeTestInvalidUser() throws Exception {
		mockMvc.perform(get("/home").with(httpBasic("invalidserid", "blahblah")))
				.andExpect(status().is4xxClientError());
	}

	@Test
	void testHomeTestValidUser() throws Exception {
		mockMvc.perform(get("/home").with(httpBasic("testuser", "testpswd"))).andExpect(status().isOk());
	}
	
	@Test
	void testHomeTestValidAdminUser() throws Exception {
		mockMvc.perform(get("/home").with(httpBasic("testadmin", "testpswd"))).andExpect(status().isOk());
	}
	
	@Test
	void testHomeTestValidNormalUser() throws Exception {
		mockMvc.perform(get("/home").with(httpBasic("testcustomer", "testpswd"))).andExpect(status().isOk());
	}
	
	//TDD: To bypass the authentication for index page url '/'.
	@Test
	void testIndexPage() throws Exception {
		mockMvc.perform(get("/")).andExpect(status().isOk());
	}
}
