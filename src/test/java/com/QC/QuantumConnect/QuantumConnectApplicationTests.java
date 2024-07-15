package com.QC.QuantumConnect;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.QC.QuantumConnect.services.EmailService;

@SpringBootTest
class QuantumConnectApplicationTests {
	@Test
	void contextLoads() {
	}

	@Autowired
	private EmailService emailService;

	@Test
	void sendEmail() {
		emailService.sendEmail("psheshu24mm@gmail.com", "Test email", "This is a test email.");
	}
}
