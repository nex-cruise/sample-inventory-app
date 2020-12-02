/*
 * Created on 02-Dec-2020
 * Created by murugan
 * Copyright � 2020 MTS [murugan425]. All Rights Reserved.
 */
package com.tamil.mts.mtsinventoryms.repositories.security;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tamil.mts.mtsinventoryms.domain.security.MTSUser;

/**
 * @author murugan
 *
 */
@Repository
public interface UserRepository extends JpaRepository<MTSUser, Integer>{

	Optional<MTSUser> findUserByUsername(String username);
}
