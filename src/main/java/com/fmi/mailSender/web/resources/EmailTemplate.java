package com.fmi.mailSender.web.resources;

import java.util.List;

import lombok.Data;

@Data
public class EmailTemplate {

	private String title;
	private String message;
	private List<String> placeholders;
 }
