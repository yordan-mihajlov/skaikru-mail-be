package com.fmi.mailSender.services;

import java.util.Map;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.text.StringSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fmi.mailSender.config.MailConfig;

@Service
public class MailServiceImpl implements MailService {

	@Autowired
	private MailConfig mailConfig;

	@Override
	public void sendMail(String from, String to, String subject, String content) {
		try {
			Message message = new MimeMessage(mailConfig.createSMTPSession());
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject(subject);
			message.setText(content);

			Transport.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public String buildMail(String mailTemplate, Map<String, String> placeholders) {
		StringSubstitutor sub = new StringSubstitutor(placeholders, "%(", ")");
		String result = sub.replace(mailTemplate);
		return result;
	}

}
