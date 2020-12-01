/*
 * Created on 01-Dec-2020
 * Created by murugan
 * Copyright � 2020 MTS [murugan425]. All Rights Reserved.
 */
package com.tamil.mts.mtsinventoryms.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author murugan
 *
 */
@Slf4j
public abstract class AbstractRestAuthFilter extends AbstractAuthenticationProcessingFilter {

	/**
	 * @param requiresAuthenticationRequestMatcher
	 */
	public AbstractRestAuthFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
		super(requiresAuthenticationRequestMatcher);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		String userId = getKey(request);
		String password = getSecret(request);

		if (null == userId)
			userId = "";
		if (null == password)
			password = "";
		log.debug("MTS: RestAuthFilter - Authenticating for userId: " + userId);
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userId, password);
		if (StringUtils.isEmpty(userId)) {
			return null;
		} else {
			return this.getAuthenticationManager().authenticate(token);
		}
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		log.debug("Request is to process authentication");

		try {
			Authentication authResult = attemptAuthentication(request, response);
			if (null == authResult) {
				chain.doFilter(request, response);
			} else {
				successfulAuthentication(request, response, chain, authResult);
			}
		} catch (AuthenticationException failed) {
			log.error("MTS: RestAuthFilter - Authenticating request failed.");
			unsuccessfulAuthentication(request, response, failed);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		log.debug("Authentication success. Updating SecurityContextHolder to contain: " + authResult);

		SecurityContextHolder.getContext().setAuthentication(authResult);
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		SecurityContextHolder.clearContext();

		log.debug("Authentication request failed: " + failed.toString(), failed);
		log.debug("Updated SecurityContextHolder to contain null Authentication");

		log.debug("No failure URL set, sending 401 Unauthorized error");
		response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
	}

	protected abstract String getKey(HttpServletRequest request);

	protected abstract String getSecret(HttpServletRequest request);
}
