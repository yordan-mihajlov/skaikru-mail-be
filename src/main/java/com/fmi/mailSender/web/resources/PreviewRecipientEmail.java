package com.fmi.mailSender.web.resources;

import lombok.Data;

@Data
public class PreviewRecipientEmail {

	private String email;
	private String message;
}
