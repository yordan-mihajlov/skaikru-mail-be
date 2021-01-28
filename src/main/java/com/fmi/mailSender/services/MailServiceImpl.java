package com.fmi.mailSender.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.text.StringSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fmi.mailSender.config.MailConfig;
import com.fmi.mailSender.utils.TemplatesUtils;
import com.fmi.mailSender.web.resources.EmailRecord;

@Service
public class MailServiceImpl implements MailService {

	@Value("${email.sent.dir}")
	private String emailSentDir;

	@Autowired
	private MailConfig mailConfig;

	@Override
	public void sendMail(String from, String to, String subject, String content, boolean isHtml) {
		try {
			Message message = new MimeMessage(mailConfig.createSMTPSession());
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject(subject);
			if (isHtml) {
				message.setContent(content, "text/html");
			} else {
				message.setText(content);
			}

			Transport.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
			saveToSent(to, subject, content, false);
			throw new RuntimeException(e);
		}
		saveToSent(to, subject, content, true);
	}

	@Override
	public String buildMail(String mailTemplate, Map<String, String> placeholders) {
		StringSubstitutor sub = new StringSubstitutor(placeholders, "%(", ")");
		String result = sub.replace(mailTemplate);
		return result;
	}

	private void saveToSent(String email, String title, String message, boolean isSuccessful) {
		EmailRecord emailRecord = new EmailRecord();
		emailRecord.setEmail(email);
		emailRecord.setMessage(message);
		emailRecord.setTitle(title);
		emailRecord.setSuccessful(isSuccessful);
		
		try {
			TemplatesUtils.OBJECT_MAPPER.writeValue(new File(emailSentDir + email + "-" + title + ".json"),
					emailRecord);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public List<EmailRecord> fetchHistory() {
		try {
			return Files.list(Paths.get(emailSentDir)).map(file -> {
				try {
					return TemplatesUtils.OBJECT_MAPPER
							.readValue(file.toFile(), EmailRecord.class);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			})
					.collect(Collectors.toList());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
