package com.fmi.mailSender.services;

import java.util.Map;

public interface MailService {

	void sendMail(String from, String to, String subject, String content, boolean isHtml);

	String buildMail(String mailTemplate, Map<String, String> placeholders);
}
