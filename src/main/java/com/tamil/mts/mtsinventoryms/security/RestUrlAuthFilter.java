/*
 * Created on 01-Dec-2020
 * Created by murugan
 * Copyright � 2020 MTS [murugan425]. All Rights Reserved.
 */
package com.tamil.mts.mtsinventoryms.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * @author murugan
 *
 */
public class RestUrlAuthFilter extends AbstractRestAuthFilter {

	/**
	 * @param requiresAuthenticationRequestMatcher
	 */
	public RestUrlAuthFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
		super(requiresAuthenticationRequestMatcher);
	}

	/**
	 * @param request
	 * @return
	 */
	@Override
	protected String getKey(HttpServletRequest request) {
		return request.getParameter("Api-Key");
	}

	/**
	 * @param request
	 * @return
	 */
	@Override
	protected String getSecret(HttpServletRequest request) {
		return request.getParameter("Api-Secret");
	}
}
