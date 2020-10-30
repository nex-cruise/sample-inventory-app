/*
 * Created on 30-Oct-2020
 * Created by murugan
 * Copyright � 2020 MTS [murugan425]. All Rights Reserved.
 */
package com.tamil.mts.mtsinventoryms.domain;

import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author murugan
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper=true)
public class Contact extends BaseEntity {

	String contactName;

	String companyName;

	String firstName;

	String lastName;

	String email;

	String contactNumber;

	String facebookId;

	String twitterId;

	String languageCode;

	/**
	 * @param id
	 * @param version
	 * @param createdDate
	 * @param lastModifiedDate
	 * @param contactName
	 * @param companyName
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param contactNumber
	 * @param facebookId
	 * @param twitterId
	 * @param languageCode
	 */
	@Builder(builderMethodName = "contactBuilder")
	public Contact(UUID id, Long version, Timestamp createdDate, Timestamp lastModifiedDate, String contactName,
			String companyName, String firstName, String lastName, String email, String contactNumber,
			String facebookId, String twitterId, String languageCode) {
		super(id, version, createdDate, lastModifiedDate);
		this.contactName = contactName;
		this.companyName = companyName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.contactNumber = contactNumber;
		this.facebookId = facebookId;
		this.twitterId = twitterId;
		this.languageCode = languageCode;
	}

}
