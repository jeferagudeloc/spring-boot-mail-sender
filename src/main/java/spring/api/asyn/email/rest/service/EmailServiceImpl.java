package spring.api.asyn.email.rest.service;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.SendFailedException;

import lombok.var;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import spring.api.asyn.email.rest.model.EmailBody;

@Service
public class EmailServiceImpl implements EmailService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);

	private JavaMailSender sender;

	@Autowired
	public EmailServiceImpl(final JavaMailSender sender){
		this.sender = sender;
	}

	@PostConstruct
	private void postConstruct() {
		LOGGER.info("PostConstruct: "+this.getClass());
	}

	@Override
	public Boolean sendEmail(EmailBody emailBody)  {
		LOGGER.info("EmailBody: {}", emailBody.toString());
		return sendEmailTool(emailBody.getContent(),emailBody.getEmail(), emailBody.getSubject(),true);
	}
	

	private boolean sendEmailTool(String textMessage, String email,String subject, boolean isHtml) {
		var send = false;
		var message = sender.createMimeMessage();
		var helper = new MimeMessageHelper(message);
		try {
			helper.setTo(email);
			helper.setText(textMessage, isHtml);
			helper.setSubject(subject);
			sender.send(message);
			LOGGER.info("Mail enviado!");
			send = true;
		}catch (SendFailedException e){
			LOGGER.error("Hubo un error en el envio del mail: {}", e);
		} catch (MessagingException e) {
			LOGGER.error("Hubo un error al enviar el mail: {}", e);
		} catch (Exception e) {
			LOGGER.error("Hubo un error no esperado: {}", e);
		}
		return send;
	}
}
