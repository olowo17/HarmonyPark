package com.harmonypark.harmonypark.service;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class EmailServiceImp implements EmailSerivce{
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImp.class);

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;
    @Override
    public void sendMail(String receiverEmail, String subject, String emailBody, String contentType) throws IOException {
        try {
            LOGGER.info("**** Beginning of log ****");
            LOGGER.info("Sending mail to: " + receiverEmail);

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
            helper.setTo(receiverEmail);
            helper.setSubject(subject);
            helper.setText(emailBody, true); // Set the second argument to true to indicate HTML content
            helper.setFrom(senderEmail); // Set the sender's email address

// Set the content type to "text/html"
            mimeMessage.setHeader("Content-Type", "text/html; charset=UTF-8");

// Send the email
            javaMailSender.send(mimeMessage);

            LOGGER.info("Email sent to: " + receiverEmail);
            LOGGER.info("**** End of log ****");
            new ResponseEntity<>("Email sent", HttpStatus.OK);

        } catch (Exception e) {
            LOGGER.error("Error sending email to " + receiverEmail, e);
            new ResponseEntity<>("An error occurred while sending the email", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
