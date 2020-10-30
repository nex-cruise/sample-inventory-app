/*
 * Created on 30-Oct-2020
 * Created by murugan
 * Copyright � 2020 MTS [murugan425]. All Rights Reserved.
 */
package com.tamil.mts.mtsinventoryms.repositories;

import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.tamil.mts.mtsinventoryms.domain.Contact;

/**
 * @author murugan
 *
 */
public interface ContactRepository extends PagingAndSortingRepository<Contact, UUID>{

}
