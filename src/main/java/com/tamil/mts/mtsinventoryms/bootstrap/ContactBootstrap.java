/*
 * Created on 07-Oct-2020
 * Created by murugan
 * Copyright � 2020 MTS [murugan425]. All Rights Reserved.
 */
package com.tamil.mts.mtsinventoryms.bootstrap;

import java.util.stream.IntStream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.tamil.mts.mtsinventoryms.services.ContactService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author murugan
 *
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ContactBootstrap implements CommandLineRunner {

	private final ContactService contactService;

	private final DataProducer dataProducer;

	@Override
	public void run(String... args) throws Exception {
		loadContacts();
	}

	private void loadContacts() {
		log.info(this.getClass().getSimpleName() + ": loadContacts()");
		IntStream.range(1, 10).parallel()
				.forEach(i -> contactService.saveNewContact(dataProducer.getValidContactDto()));
		log.info("Total Contacts Loaded : %s", contactService.getTotalContactCount().toString());
	}
}
