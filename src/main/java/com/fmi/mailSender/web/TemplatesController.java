package com.fmi.mailSender.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fmi.mailSender.services.TemplatesService;
import com.fmi.mailSender.web.resources.EmailTemplate;

@RestController
public class TemplatesController {

	@Autowired
	private TemplatesService templatesService;

	@PostMapping(path = "/add-template", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void addTemplate(@RequestBody EmailTemplate emailTemplate) {
		templatesService.saveTemplate(emailTemplate);
	}
	
	@GetMapping(path = "/fetch-templates", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<EmailTemplate> fetchTemplate() {
		return templatesService.fetchTemplates();
	}
	
	@DeleteMapping(path = "/{title}/delete-template", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteTemplate(@PathVariable(value = "title") String title) {
		templatesService.deleteTemplate(title);
	}
}
