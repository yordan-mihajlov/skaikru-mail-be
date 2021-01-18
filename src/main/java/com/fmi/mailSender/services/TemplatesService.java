package com.fmi.mailSender.services;


import java.util.List;

import com.fmi.mailSender.web.resources.EmailTemplate;

public interface TemplatesService {

	void saveTemplate(EmailTemplate emailTemplate);
	
	List<EmailTemplate> fetchTemplates();

	void deleteTemplate(String title);

}
