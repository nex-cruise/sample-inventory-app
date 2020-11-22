/*
 * Created on 22-Nov-2020
 * Created by murugan
 * Copyright � 2020 MTS [murugan425]. All Rights Reserved.
 */
package com.tamil.mts.mtsinventoryms.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author murugan
 *
 */
@Controller
public class HomeController {

	@GetMapping({ "", "/" })
	public String index() {
		return "index";
	}

	@GetMapping({ "home" })
	public String home() {
		return "home";
	}
}
