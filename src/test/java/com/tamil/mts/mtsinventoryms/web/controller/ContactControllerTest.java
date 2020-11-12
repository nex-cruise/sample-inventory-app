/*
 * Created on 01-Nov-2020
 * Created by murugan
 * Copyright � 2020 MTS [murugan425]. All Rights Reserved.
 */
package com.tamil.mts.mtsinventoryms.web.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tamil.mts.mtsinventoryms.bootstrap.DataProducer;
import com.tamil.mts.mtsinventoryms.services.ContactService;
import com.tamil.mts.mtsinventoryms.web.model.ContactDto;

/**
 * @author murugan
 *
 */
@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "mtsapps.in", uriPort = 80)
@WebMvcTest(ContactController.class)
public class ContactControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@MockBean
	ContactService contactService;

	private final String CONTACT_API_PATH = "/mts/api/v1/contact/";

	@Test
	public void getEmployeeById() throws Exception {
		given(contactService.getContactById(any(UUID.class))).willReturn(DataProducer.getValidContactDto());

		ConstrainedFields fields = new ConstrainedFields(ContactDto.class);
		
		mockMvc.perform(get(CONTACT_API_PATH + "{contactId}", UUID.randomUUID().toString()).param("alldetails", "yes")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andDo(document("mts/api/v1/contact/get",
						pathParameters(parameterWithName("contactId").description("UUID of the desired Contact Id.")),
						responseFields(fields.withPath("uid").description("Contact Unique Id"),
								fields.withPath("version").description("Version number"),
								fields.withPath("contactName").description("Contact Name"),
								fields.withPath("companyName").description("Contact Company/Business Name"),
								fields.withPath("firstName").description("Contact First Name"),
								fields.withPath("lastName").description("Contact Last Name"),
								fields.withPath("email").description("Contact Email"),
								fields.withPath("contactNumber").description("Contact Number"),
								fields.withPath("facebookId").description("Facebook Id"),
								fields.withPath("twitterId").description("Twitter Id"),
								fields.withPath("languageCode").description("Language Codes [like - en, ta, zh]"),
								fields.withPath("createdDate").description("Created Date"),
								fields.withPath("modifiedDate").description("Date Updated"))));
	}

	@Test
	public void createValidEmployee() throws Exception {
		ContactDto contactDto = DataProducer.getNewContactDto();
		String contactDtoJson = objectMapper.writeValueAsString(contactDto);
		given(contactService.saveNewContact(any(ContactDto.class))).willReturn(DataProducer.getValidContactDto());

		ConstrainedFields fields = new ConstrainedFields(ContactDto.class);

		mockMvc.perform(post(CONTACT_API_PATH).contentType(MediaType.APPLICATION_JSON).content(contactDtoJson))
				.andExpect(status().isCreated())
				.andDo(document("mts/api/v1/contact/post",
						requestFields(fields.withPath("uid").ignored(), fieldWithPath("version").ignored(),
								fields.withPath("createdDate").ignored(), fieldWithPath("modifiedDate").ignored(),
								fields.withPath("contactName").description("Contact Name"),
								fields.withPath("companyName").description("Contact Company/Business Name"),
								fields.withPath("firstName").description("Contact First Name"),
								fields.withPath("lastName").description("Contact Last Name"),
								fields.withPath("email").description("Contact Email"),
								fields.withPath("contactNumber").description("Contact Number"),
								fields.withPath("facebookId").description("Facebook Id"),
								fields.withPath("twitterId").description("Twitter Id"),
								fields.withPath("languageCode").description("Language Codes [like - en, ta, zh]"))));
	}

	@Test
	public void createInvalidEmployee() throws Exception {
		ContactDto contactDto = DataProducer.getInvalidContactDto();
		String contactDtoJson = objectMapper.writeValueAsString(contactDto);
		given(contactService.saveNewContact(any(ContactDto.class))).willReturn(DataProducer.getValidContactDto());
		mockMvc.perform(post(CONTACT_API_PATH).contentType(MediaType.APPLICATION_JSON).content(contactDtoJson))
				.andExpect(status().is4xxClientError());
	}

	private static class ConstrainedFields {

		private final ConstraintDescriptions constraintDescriptions;

		ConstrainedFields(Class<?> input) {
			this.constraintDescriptions = new ConstraintDescriptions(input);
		}

		private FieldDescriptor withPath(String path) {
			return fieldWithPath(path).attributes(key("constraints").value(StringUtils
					.collectionToDelimitedString(this.constraintDescriptions.descriptionsForProperty(path), ". ")));
		}
	}

}
