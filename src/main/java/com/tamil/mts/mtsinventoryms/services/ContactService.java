/*
 * Created on 30-Oct-2020
 * Created by murugan
 * Copyright � 2020 MTS [murugan425]. All Rights Reserved.
 */
package com.tamil.mts.mtsinventoryms.services;

import java.util.UUID;

import com.tamil.mts.mtsinventoryms.web.model.ContactDto;

/**
 * @author murugan
 *
 */
public interface ContactService {

	Long getTotalContactCount();
	
	ContactDto getContactById(UUID contactId);

	ContactDto saveNewContact(ContactDto contactDto);

	ContactDto updateContact(UUID empId, ContactDto contactDto);

	void deleteContactById(UUID empId);
	
}
