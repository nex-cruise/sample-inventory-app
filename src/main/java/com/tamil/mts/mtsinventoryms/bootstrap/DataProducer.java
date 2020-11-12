/*
 * Created on 17-Oct-2020
 * Created by murugan
 * Copyright � 2020 MTS [murugan425]. All Rights Reserved.
 */
package com.tamil.mts.mtsinventoryms.bootstrap;

import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Component;

import com.tamil.mts.mtsinventoryms.web.model.ContactDto;

import lombok.extern.slf4j.Slf4j;

/**
 * @author murugan
 *
 */
@Slf4j
@Component
public class DataProducer {

	public static ContactDto getValidContactDto() {
		ContactDto contact = ContactDto.contactBuilder().id(UUID.randomUUID())
				.companyName(RandomStringUtils.randomAlphanumeric(3, 50))
				.contactName(RandomStringUtils.randomAlphabetic(3, 50)).contactNumber(getValidMobileNumber())
				.firstName(RandomStringUtils.randomAlphabetic(3, 30))
				.lastName(RandomStringUtils.randomAlphabetic(3, 30))
				.facebookId("fb-" + RandomStringUtils.randomAlphanumeric(5, 10))
				.twitterId("tw-" + RandomStringUtils.randomAlphanumeric(5, 10)).build();
		contact.setEmail(
				RandomStringUtils.randomAlphanumeric(3, 15) + "@" + contact.getCompanyName().substring(0, 3) + ".com");
		log.info("Valid Contact generated: " + contact.toString());
		return contact;
	}

	public static ContactDto getNewContactDto() {
		ContactDto contact = ContactDto.contactBuilder().companyName(RandomStringUtils.randomAlphanumeric(3, 50))
				.contactName(RandomStringUtils.randomAlphabetic(3, 50)).contactNumber(getValidMobileNumber())
				.firstName(RandomStringUtils.randomAlphabetic(3, 30))
				.lastName(RandomStringUtils.randomAlphabetic(3, 30))
				.facebookId("fb-" + RandomStringUtils.randomAlphanumeric(5, 10))
				.twitterId("tw-" + RandomStringUtils.randomAlphanumeric(5, 10)).build();
		contact.setEmail(
				RandomStringUtils.randomAlphanumeric(3, 15) + "@" + contact.getCompanyName().substring(0, 3) + ".com");
		log.info("Valid New Contact generated: " + contact.toString());
		return contact;
	}

	public static ContactDto getInvalidContactDto() {
		ContactDto contact = ContactDto.contactBuilder().companyName(RandomStringUtils.randomAlphanumeric(3, 100))
				.contactName(RandomStringUtils.randomAlphabetic(51, 100)).contactNumber(getValidMobileNumber())
				.firstName(RandomStringUtils.randomAlphabetic(2, 40))
				.facebookId("fb-" + RandomStringUtils.randomAlphanumeric(5, 10)).build();
		contact.setEmail(RandomStringUtils.randomAlphanumeric(3, 15));
		log.info("Invalid Contact generated: " + contact.toString());
		return contact;
	}

	public static String getValidMobileNumber() {
		return String.valueOf((int) RandomUtils.nextDouble(Math.pow(10, 9), Math.pow(10, 10)));
	}

}
