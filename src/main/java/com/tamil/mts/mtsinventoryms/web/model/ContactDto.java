/*
 * Created on 30-Oct-2020
 * Created by murugan
 * Copyright � 2020 MTS [murugan425]. All Rights Reserved.
 */
package com.tamil.mts.mtsinventoryms.web.model;

import java.time.OffsetDateTime;
import java.util.UUID;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

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
@EqualsAndHashCode(callSuper = true)
public class ContactDto extends BaseDto {

	@NotBlank
	@Size(min = 3, max = 50)
	@JsonProperty("contactName")
	String contactName;

	@NotBlank
	@Size(min = 3, max = 50)
	@JsonProperty("companyName")
	String companyName;

	@NotBlank
	@Size(min = 3, max = 30)
	@JsonProperty("firstName")
	String firstName;

	@NotEmpty
	@Size(min = 3, max = 30)
	@JsonProperty("lastName")
	String lastName;

	@NotEmpty
	@Email
	@JsonProperty("email")
	@JsonAlias({"contactEmail"})
	String email;

	@NotBlank
	@Pattern(regexp = "^(\\d{3}[- .]?){2}\\d{4}$")
	@JsonProperty("contactNumber")
	@JsonAlias({"phoneNumber"})
	String contactNumber;

	String facebookId;

	String twitterId;

	//TODO - Implement custom validator to check the languageCode
	@Size(min = 2, max = 2)
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
	public ContactDto(UUID id, Integer version, OffsetDateTime createdDate, OffsetDateTime lastModifiedDate,
			String contactName, String companyName, String firstName, String lastName, String email,
			String contactNumber, String facebookId, String twitterId, String languageCode) {
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
