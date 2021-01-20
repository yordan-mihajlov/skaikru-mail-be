package com.fmi.mailSender.web.resources;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class SendMailRequest extends EmailTemplate{
	private List<Recipient> recipients;
}
