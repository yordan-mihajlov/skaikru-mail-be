package com.fmi.mailSender.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fmi.mailSender.utils.TemplatesUtils;
import com.fmi.mailSender.web.resources.EmailTemplate;

@Service
public class TemplatesServiceImpl implements TemplatesService {

	@Value("${templates.dir}")
	private String templatesDir;
	@Value("${templates.trash.dir}")
	private String templatesTrashDir;

	@Override
	public void saveTemplate(EmailTemplate emailTemplate) {
		try {
			TemplatesUtils.OBJECT_MAPPER.writeValue(new File(templatesDir + emailTemplate.getTitle() + ".json"),
					emailTemplate);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<EmailTemplate> fetchTemplates() {
		try {
			return Files.list(Paths.get(templatesDir)).map(file -> {
				try {
					return TemplatesUtils.OBJECT_MAPPER
							.readValue(file.toFile(), EmailTemplate.class);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			})
					.collect(Collectors.toList());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void deleteTemplate(String title) {
		try {
			Files.move(Paths.get(templatesDir + title + ".json"), Paths.get(templatesTrashDir + title + ".json"), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
