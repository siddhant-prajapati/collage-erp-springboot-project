package com.collage.collageerp.Services;

import com.collage.collageerp.entity.EmailDetails;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}") private String sender;

    // Method 1
    // To send a simple email
    @Override
    public String sendSimpleMail(EmailDetails details) {
        // Try block to check for exceptions
        try {
            System.out.println(details);
            SimpleMailMessage mailMessage= new SimpleMailMessage();

            // Setting up necessary details

            mailMessage.setFrom(details.getRecipient());   //i have change the sender with recipient
            mailMessage.setTo(sender); //so that sender can easily send message

            mailMessage.setText(details.getMsgBody());
            mailMessage.setSubject(details.getSubject());

            String oldMessage = mailMessage.getText();
            String newMessage = oldMessage + "\n\n Message comming from "+ mailMessage.getFrom();
            mailMessage.setText(newMessage);

            // Sending the mail
            javaMailSender.send(mailMessage);
            //System.out.println(mailMessage);
            return "Mail Sent Successfully...";
        }

        // Catch block to handle the exceptions
        catch (Exception e) {
            System.out.println(e);
            return "Error while Sending Mail";
        }
    }

    // Method 2
    // To send an email with attachment
//    @Bean
//    @Override
//    public String sendMailWithAttachment(EmailDetails details) {
//        // Creating a mime message
//        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//        MimeMessageHelper mimeMessageHelper;
//
//        try {
//
//            // Setting multipart as true for attachments to
//            // be send
//            mimeMessageHelper
//                    = new MimeMessageHelper(mimeMessage, true);
//            mimeMessageHelper.setFrom(sender);
//            mimeMessageHelper.setTo(details.getRecipient());
//            mimeMessageHelper.setText(details.getMsgBody());
//            mimeMessageHelper.setSubject(
//                    details.getSubject());
//
//            // Adding the attachment
//            FileSystemResource file
//                    = new FileSystemResource(
//                    new File(details.getAttachment()));
//
//            mimeMessageHelper.addAttachment(
//                    file.getFilename(), file);
//            System.out.println(mimeMessage);
//            // Sending the mail
//            javaMailSender.send(mimeMessage);
//            return "Mail sent Successfully";
//        }
//
//        // Catch block to handle MessagingException
//        catch (MessagingException e) {
//
//            // Display message when exception occurred
//            return "Error while sending mail!!!";
//        }
//    }
}
