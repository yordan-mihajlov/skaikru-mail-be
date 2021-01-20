package com.fmi.mailSender.web;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class HomeController {

	@RequestMapping("/")
	public String index() {
		return "Greetings from Spring Boot!";
	}

}