package com.fmi.mailSender.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fmi.mailSender.services.MailService;
import com.fmi.mailSender.web.resources.Recipient;
import com.fmi.mailSender.web.resources.SendMailRequest;

@RestController
public class MailController {
	
	@Value("${username}")
	private String username;
	
	@Autowired
	private MailService mailService;

	@PostMapping(path= "/send-mails", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Integer sendMail(@RequestBody SendMailRequest sendMailRequest) {
		
		int errorsCount = 0;
		
		for(Recipient recipient : sendMailRequest.getEmails()) {
			try{
				mailService.sendMail(username, recipient.getEmail(), sendMailRequest.getTitle(), 
								mailService.buildMail(sendMailRequest.getMessage(), recipient.getPlaceholders()));
			} catch (Exception e) {
				errorsCount++;
			}
		}

		return sendMailRequest.getEmails().size() - errorsCount;
	}
}
