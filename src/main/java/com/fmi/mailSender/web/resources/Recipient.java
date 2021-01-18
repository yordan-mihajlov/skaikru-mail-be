package com.fmi.mailSender.web.resources;

import java.util.Map;

import lombok.Data;

@Data
public class Recipient {
	private String email;
	private Map<String, String> placeholders;

}
