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
public class RestHeaderAuthFilter extends AbstractAuthenticationProcessingFilter {

	/**
	 * @param requiresAuthenticationRequestMatcher
	 */
	public RestHeaderAuthFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
		super(requiresAuthenticationRequestMatcher);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		String userId = getKeyFromHeader(request);
		String password = getSecretFromHeader(request);

		if (null == userId)
			userId = "";
		if (null == password)
			password = "";
		log.debug("MTS: RestHeaderAuthFilter - Authenticatingfor userId: " + userId);
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userId, password);
		if (StringUtils.isEmpty(userId)) {
			return null;
		} else {
			return this.getAuthenticationManager().authenticate(token);
		}
	}

	/**
	 * @param request
	 * @return
	 */
	private String getKeyFromHeader(HttpServletRequest request) {
		return request.getHeader("Api-Key");
	}

	/**
	 * @param request
	 * @return
	 */
	private String getSecretFromHeader(HttpServletRequest request) {
		return request.getHeader("Api-Secret");
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		if (logger.isDebugEnabled()) {
			logger.debug("Request is to process authentication");
		}

		Authentication authResult = attemptAuthentication(request, response);

		if (null == authResult) {
			chain.doFilter(request, response);
		} else {
			successfulAuthentication(request, response, chain, authResult);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		if (logger.isDebugEnabled()) {
			logger.debug("Authentication success. Updating SecurityContextHolder to contain: " + authResult);
		}

		SecurityContextHolder.getContext().setAuthentication(authResult);
	}
}
