package com.QC.QuantumConnect.services;

public interface EmailService {
    void sendEmail(String to, String subject, String body);
}
