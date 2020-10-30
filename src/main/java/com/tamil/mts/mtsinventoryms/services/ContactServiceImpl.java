/*
 * Created on 30-Oct-2020
 * Created by murugan
 * Copyright � 2020 MTS [murugan425]. All Rights Reserved.
 */
package com.tamil.mts.mtsinventoryms.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.tamil.mts.mtsinventoryms.domain.Contact;
import com.tamil.mts.mtsinventoryms.mapper.ContactMapper;
import com.tamil.mts.mtsinventoryms.repositories.ContactRepository;
import com.tamil.mts.mtsinventoryms.web.model.ContactDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author murugan
 *
 */
@Slf4j
@RequiredArgsConstructor
@Service("contactService")
public class ContactServiceImpl implements ContactService {
	
	private final ContactRepository contactRepository;

	private final ContactMapper contactMapper;

	@Override
	public Long getTotalContactCount() {
		return contactRepository.count();
	}

	@Override
	public ContactDto getContactById(UUID contactId) {
		return contactMapper.convertToModel(contactRepository.findById(contactId).get());
	}

	@Override
	public ContactDto saveNewContact(ContactDto contactDto) {
		return contactMapper.convertToModel(contactRepository.save(contactMapper.convertToDomain(contactDto)));
	}

	@Override
	public ContactDto updateContact(UUID contactId, ContactDto contactDto) {
		Contact contact = contactRepository.findById(contactId).get();
        contact.setCompanyName( contactDto.getCompanyName() );
        contact.setContactName( contactDto.getContactName() );
        contact.setContactNumber( contactDto.getContactNumber() );
        contact.setEmail( contactDto.getEmail() );
        contact.setFacebookId( contactDto.getFacebookId() );
        contact.setFirstName( contactDto.getFirstName() );
        contact.setLanguageCode( contactDto.getLanguageCode() );
        contact.setLastName( contactDto.getLastName() );
        contact.setTwitterId( contactDto.getTwitterId() );
		return contactMapper.convertToModel(contactRepository.save(contact));
	}

	@Override
	public void deleteContactById(UUID contactId) {
		log.info("TODO: Implement delete Employee");
		contactRepository.deleteById(contactId);
	}
}
