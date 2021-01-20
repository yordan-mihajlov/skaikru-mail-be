package com.fmi.mailSender.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fmi.mailSender.services.TemplatesService;
import com.fmi.mailSender.web.resources.EmailTemplate;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class TemplatesController {

	@Autowired
	private TemplatesService templatesService;

	@PostMapping(path = "/add-template", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EmailTemplate> addTemplate(@RequestBody EmailTemplate emailTemplate) {
		templatesService.saveTemplate(emailTemplate);

		return new ResponseEntity<>(emailTemplate, HttpStatus.CREATED);
	}

	@GetMapping(path = "/fetch-templates", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<EmailTemplate>> fetchTemplate() {
		return new ResponseEntity<>(templatesService.fetchTemplates(), HttpStatus.OK);
	}

	@DeleteMapping(path = "/{title}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> deleteTemplate(@PathVariable(value = "title") String title) {
		templatesService.deleteTemplate(title);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
