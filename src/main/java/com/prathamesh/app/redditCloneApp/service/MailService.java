package com.prathamesh.app.redditCloneApp.service;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.prathamesh.app.redditCloneApp.exceptions.SpringRedditException;
import com.prathamesh.app.redditCloneApp.model.NotificationEmail;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class MailService {

	private final JavaMailSender javaMailSender;
	private final MailContentBuilder mailContentBuilder;
	
	@Async
	void sendMail(NotificationEmail notificationEmail) {
		MimeMessagePreparator messagePreparator = mimeMessage ->{
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
			messageHelper.setFrom("springreddit@email.com");
			messageHelper.setTo(notificationEmail.getRecipient());
			messageHelper.setSubject(notificationEmail.getRecipient());
			messageHelper.setText(mailContentBuilder.build(notificationEmail.getBody()));
		};
		try {
			javaMailSender.send(messagePreparator);
			log.info("Activation Email Sent!!");
		}
		catch(MailException e) {
			throw new SpringRedditException("Exception occured when sending mail to" + notificationEmail.getRecipient(),e);
		}
	}
}
