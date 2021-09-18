package spring.api.asyn.email.rest.model;

import lombok.Data;

@Data
public class EmailBody {
	private String email;
	private String content;
	private String subject;
}
