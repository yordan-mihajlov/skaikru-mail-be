package com.fmi.mailSender.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fmi.mailSender.services.MailService;
import com.fmi.mailSender.web.resources.PreviewRecipientEmail;
import com.fmi.mailSender.web.resources.Recipient;
import com.fmi.mailSender.web.resources.SendMailRequest;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class MailController {

	@Value("${username}")
	private String username;

	@Autowired
	private MailService mailService;

	@PostMapping(path = "/send-mails", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> sendMail(@RequestBody SendMailRequest sendMailRequest) {

		int errorsCount = 0;

		for (Recipient recipient : sendMailRequest.getRecipients()) {
			try {
				mailService.sendMail(username, recipient.getEmail(), sendMailRequest.getTitle(),
						mailService.buildMail(sendMailRequest.getMessage(), recipient.getPlaceholders()));
			} catch (Exception e) {
				errorsCount++;
			}
		}

		return new ResponseEntity<>(sendMailRequest.getRecipients().size() - errorsCount, HttpStatus.ACCEPTED);
	}

	@PostMapping(path = "/preview-mails", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<PreviewRecipientEmail>> previewMail(@RequestBody SendMailRequest sendMailRequest) {

		List<PreviewRecipientEmail> previewRecipientEmails = new ArrayList<>();

		for (Recipient recipient : sendMailRequest.getRecipients()) {
			PreviewRecipientEmail previewRecipientEmail = new PreviewRecipientEmail();
			String message = mailService.buildMail(sendMailRequest.getMessage(), recipient.getPlaceholders());
			previewRecipientEmail.setEmail(recipient.getEmail());
			previewRecipientEmail.setMessage(message);
			previewRecipientEmails.add(previewRecipientEmail);
		}
		return new ResponseEntity<>(previewRecipientEmails, HttpStatus.OK);
	}
}
