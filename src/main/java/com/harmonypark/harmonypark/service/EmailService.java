package com.harmonypark.harmonypark.service;

import java.io.IOException;

public interface EmailService {
    void sendMail(String receiverEmail, String subject, String emailBody, String contentType) throws IOException;
}
