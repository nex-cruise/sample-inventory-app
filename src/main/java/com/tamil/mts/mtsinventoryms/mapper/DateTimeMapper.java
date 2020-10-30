/*
 * Created on 09-Oct-2020
 * Created by murugan
 * Copyright � 2020 MTS [murugan425]. All Rights Reserved.
 */
package com.tamil.mts.mtsinventoryms.mapper;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import org.springframework.stereotype.Component;

/**
 * @author murugan
 *
 */
@Component
public class DateTimeMapper {

	public OffsetDateTime convertToOffsetDateTime(Timestamp timestamp) {
		if (timestamp == null){
			return null;
		}
		return OffsetDateTime.ofInstant(Instant.ofEpochMilli(timestamp.getTime()), ZoneId.of("UTC"));
	}
	
	public Timestamp convertToTimestamp(OffsetDateTime offsetDateTime) {
		if (offsetDateTime == null){
			return null;
		}
		return Timestamp.valueOf(offsetDateTime.atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime());
	}
}
