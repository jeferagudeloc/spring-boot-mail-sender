package spring.api.asyn.email.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import spring.api.asyn.email.rest.model.EmailBody;
import spring.api.asyn.email.rest.service.EmailService;

@RestController
@RequestMapping(value = "/email")
public class EmailController {
	
	@Autowired
	private EmailService emailService;
	
	@PostMapping(value = "/send")
	public Mono<Boolean> SendEmail(@RequestBody EmailBody emailBody)  {
		return Mono.just(emailService.sendEmail(emailBody));
	}
	
}
