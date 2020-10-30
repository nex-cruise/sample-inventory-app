/*
 * Created on 30-Oct-2020
 * Created by murugan
 * Copyright � 2020 MTS [murugan425]. All Rights Reserved.
 */
package com.tamil.mts.mtsinventoryms.mapper;

import org.mapstruct.Mapper;

import com.tamil.mts.mtsinventoryms.domain.Contact;
import com.tamil.mts.mtsinventoryms.web.model.ContactDto;

/**
 * @author murugan
 *
 */
@Mapper(uses = {DateTimeMapper.class})
public interface ContactMapper {

	ContactDto convertToModel(Contact contact);

	Contact convertToDomain(ContactDto contactDto);
	
}
