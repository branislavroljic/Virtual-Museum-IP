package org.unibl.etf.services.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.unibl.etf.services.MailService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Component
public class MailServiceImpl implements MailService {

    private final JavaMailSender javaMailSender;
    private final static String SUBJECT = "Virtual Museum - Ticket";
    private final static String TEXT = "Hey, here is your ticket!";

    @Value("${spring.mail.username}")
    private String from;

    public MailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }


    @Override
    public void sendTicket(String to, String ticketPath) throws MessagingException {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(SUBJECT);
        helper.setText(TEXT);

        FileSystemResource file = new FileSystemResource(new File(ticketPath));
        helper.addAttachment("VM Ticket.pdf", file);

        javaMailSender.send(message);
    }

    @Override
    public void sendMail(String to, String text){

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(from);
        message.setTo(to);
        message.setSubject(SUBJECT);
        message.setText(text);

        javaMailSender.send(message);
    }
}
