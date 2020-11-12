/*
 * Created on 30-Oct-2020
 * Created by murugan
 * Copyright � 2020 MTS [murugan425]. All Rights Reserved.
 */
package com.tamil.mts.mtsinventoryms.web.controller;

import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tamil.mts.mtsinventoryms.services.ContactService;
import com.tamil.mts.mtsinventoryms.web.model.ContactDto;

import lombok.RequiredArgsConstructor;
import lombok.val;

/**
 * @author murugan
 *
 */
@Validated
@RequiredArgsConstructor
@RequestMapping("mts/api/v1/contact")
@RestController
public class ContactController {

	private final ContactService contactService;

	@GetMapping({ "/{contactId}" })
	public ResponseEntity<ContactDto> getContactById(@NotNull @PathVariable("contactId") UUID contactId) {
		return new ResponseEntity<>(contactService.getContactById(contactId), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<ContactDto> createContact(@Valid @NotNull @RequestBody ContactDto contactDto) {
		val savedContactDto = contactService.saveNewContact(contactDto);
		val headers = new HttpHeaders();
		headers.add("Location", "mts/api/v1/contact/" + savedContactDto.getId().toString());
		return new ResponseEntity<ContactDto>(headers, HttpStatus.CREATED);
	}

	@SuppressWarnings({ "rawtypes" })
	@PutMapping({ "/{contactId}" })
	public ResponseEntity updateContact(@NotNull @PathVariable("contactId") UUID contactId,
			@Valid @NotNull @RequestBody ContactDto contactDto) {
		contactService.updateContact(contactId, contactDto);
		return new ResponseEntity(HttpStatus.OK);
	}

	@SuppressWarnings({ "rawtypes" })
	@DeleteMapping({ "/{contactId}" })
	public ResponseEntity deleteContact(@NotNull @PathVariable("contactId") UUID contactId) {
		contactService.deleteContactById(contactId);
		return new ResponseEntity(HttpStatus.OK);
	}
	
}
