package com.collage.collageerp.Services;
// Java Program to Illustrate Creation Of
// Service Interface
// Importing required classes
// Interface

import com.collage.collageerp.entity.EmailDetails;
import org.springframework.stereotype.Component;

@Component
public interface EmailService {

    // Method
    // To send a simple email
    String sendSimpleMail(EmailDetails details);

    // Method
    // To send an email with attachment
    //String sendMailWithAttachment(EmailDetails details);
}
