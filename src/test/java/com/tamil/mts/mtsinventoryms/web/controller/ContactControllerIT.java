/*
 * Created on 29-Nov-2020
 * Created by murugan
 * Copyright � 2020 MTS [murugan425]. All Rights Reserved.
 */
package com.tamil.mts.mtsinventoryms.web.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tamil.mts.mtsinventoryms.bootstrap.DataProducer;
import com.tamil.mts.mtsinventoryms.services.ContactService;
import com.tamil.mts.mtsinventoryms.web.model.ContactDto;

/**
 * @author murugan
 *
 */
@SpringBootTest
@ActiveProfiles("test")
public class ContactControllerIT extends BaseSecurityIT {

	@Autowired
	ObjectMapper objectMapper;

	@MockBean
	ContactService contactService;

	private final String CONTACT_API_PATH = "/mts/api/v1/contact/";

	@Test
	@DisplayName("Test Get contact - HttpBasic Authentication")
	public void getEmployeeById() throws Exception {
		given(contactService.getContactById(any(UUID.class))).willReturn(DataProducer.getValidContactDto());

		mockMvc.perform(get(CONTACT_API_PATH + "{contactId}", UUID.randomUUID().toString())
				.with(httpBasic("testadmin", "testpswd")).param("alldetails", "yes").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	@DisplayName("Test Create contact - HttpBasic Authentication")
	public void createValidEmployee() throws Exception {
		ContactDto contactDto = DataProducer.getNewContactDto();
		String contactDtoJson = objectMapper.writeValueAsString(contactDto);
		given(contactService.saveNewContact(any(ContactDto.class))).willReturn(DataProducer.getValidContactDto());

		mockMvc.perform(post(CONTACT_API_PATH).with(httpBasic("testadmin", "testpswd"))
				.contentType(MediaType.APPLICATION_JSON).content(contactDtoJson)).andExpect(status().isCreated());
	}

	@Test
	@DisplayName("Test Delete contact - HttpBasic Authentication")
	public void deleteEmployeeByIdHttpBasic() throws Exception {
		mockMvc.perform(delete(CONTACT_API_PATH + "{contactId}", UUID.randomUUID().toString())
				.with(httpBasic("testadmin", "testpswd"))).andExpect(status().isOk());
	}

	@Test
	@DisplayName("Test Delete contact - Header Key/Secret Authentication")
	public void deleteEmployeeByIdHeaderKey() throws Exception {
		mockMvc.perform(delete(CONTACT_API_PATH + "{contactId}", UUID.randomUUID().toString())
//				.with(httpBasic("testadmin", "testpswd"))).andExpect(status().isOk());
				.header("Api-Key", "testuser").header("Api-Secret", "testpswd")).andExpect(status().isOk());
	}

	@Test
	@DisplayName("Test Delete contact - Header Invalid Key/Secret Authentication")
	public void deleteEmployeeByInvalidIdHeaderKey() throws Exception {
		mockMvc.perform(delete(CONTACT_API_PATH + "{contactId}", UUID.randomUUID().toString())
				.header("Api-Key", "invalid").header("Api-Secret", "jklhff")).andExpect(status().is4xxClientError());
	}
	
	@Test
	@DisplayName("Test Get contact - Url Key/Secret Authentication")
	public void getEmployeeByIdUrlKey() throws Exception {
		given(contactService.getContactById(any(UUID.class))).willReturn(DataProducer.getValidContactDto());

		mockMvc.perform(get(CONTACT_API_PATH + "{contactId}", UUID.randomUUID().toString())
				.param("Api-Key", "testuser").param("Api-Secret", "testpswd").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
}
