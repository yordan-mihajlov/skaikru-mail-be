package com.fmi.mailSender.services;

import java.util.List;
import java.util.Map;

import com.fmi.mailSender.web.resources.EmailRecord;

public interface MailService {

	void sendMail(String from, String to, String subject, String content, boolean isHtml);

	String buildMail(String mailTemplate, Map<String, String> placeholders);

	List<EmailRecord> fetchHistory();
}
