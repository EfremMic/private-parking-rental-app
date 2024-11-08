package com.parking.notificationservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootTest
class NotificationServiceApplicationTests {

	@Autowired
	private JavaMailSender mailSender;

	@Test
	public void testSendEmail() {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo("jupiter.walker.2023@gmail.com"); // Replace with a real email for testing
		message.setSubject("Test Email");
		message.setText("This is a test email from the notification service.");

		try {
			mailSender.send(message);
			System.out.println("Email sent successfully");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error sending email: " + e.getMessage());
		}
	}
}
