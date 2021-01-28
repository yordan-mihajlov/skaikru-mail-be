package com.fmi.mailSender.web.resources;

import lombok.Data;

@Data
public class EmailRecord {

	private String email;
	private String title;
	private String message;
	private boolean isSuccessful;
}
