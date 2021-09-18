package spring.api.asyn.email.rest.service;

import spring.api.asyn.email.rest.model.EmailBody;

public interface EmailService {
	Boolean sendEmail(EmailBody emailBody);
}
