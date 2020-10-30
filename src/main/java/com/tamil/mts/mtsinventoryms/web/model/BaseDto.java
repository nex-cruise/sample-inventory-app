/*
 * Created on 30-Oct-2020
 * Created by murugan
 * Copyright � 2020 MTS [murugan425]. All Rights Reserved.
 */
package com.tamil.mts.mtsinventoryms.web.model;

import java.time.OffsetDateTime;
import java.util.UUID;

import javax.validation.constraints.Null;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author murugan
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaseDto {
	
	@Null
	@JsonProperty("empId")
	UUID id;

	@Null
	Integer version;

	@Null
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ", shape = JsonFormat.Shape.STRING, timezone = "UTC")
	OffsetDateTime createdDate;

	@Null
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ", shape = JsonFormat.Shape.STRING, timezone = "UTC")
	@JsonProperty("modifiedDate")
	OffsetDateTime lastModifiedDate;
	
}
