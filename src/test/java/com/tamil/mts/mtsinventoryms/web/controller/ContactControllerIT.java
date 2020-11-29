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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tamil.mts.mtsinventoryms.bootstrap.DataProducer;
import com.tamil.mts.mtsinventoryms.services.ContactService;
import com.tamil.mts.mtsinventoryms.web.model.ContactDto;

/**
 * @author murugan
 *
 */
@WebMvcTest(ContactController.class)
public class ContactControllerIT extends BaseSecurityIT {

	@Autowired
	ObjectMapper objectMapper;

	@MockBean
	ContactService contactService;

	private final String CONTACT_API_PATH = "/mts/api/v1/contact/";

	@Test
	public void getEmployeeById() throws Exception {
		given(contactService.getContactById(any(UUID.class))).willReturn(DataProducer.getValidContactDto());

		mockMvc.perform(get(CONTACT_API_PATH + "{contactId}", UUID.randomUUID().toString())
				.with(httpBasic("testadmin", "testpswd")).param("alldetails", "yes").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void createValidEmployee() throws Exception {
		ContactDto contactDto = DataProducer.getNewContactDto();
		String contactDtoJson = objectMapper.writeValueAsString(contactDto);
		given(contactService.saveNewContact(any(ContactDto.class))).willReturn(DataProducer.getValidContactDto());

		mockMvc.perform(post(CONTACT_API_PATH).with(httpBasic("testadmin", "testpswd"))
				.contentType(MediaType.APPLICATION_JSON).content(contactDtoJson)).andExpect(status().isCreated());

	}

}
