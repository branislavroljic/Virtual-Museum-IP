package org.unibl.etf.admin_app.service;

import org.unibl.etf.admin_app.dao.UserDAO;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.Serializable;
import java.util.Properties;

public class MailService implements Serializable {

    private static final String FROM = "sni.dms.bane@gmail.com";
    private static final String PASS = "snidmsbane1";
    private static final String HOST = "smtp.gmail.com";
    private final static String SUBJECT = "Virtual Museum - Ticket";


    public static void sendMail(String recipientId, String text){

        String recipientMail = UserDAO.getUserMailById(recipientId);

        Properties props = new Properties();

        // Defining properties
        props.put("mail.smtp.host", HOST);
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.user", FROM);
        props.put("mail.password", PASS);
        props.put("mail.smtp.port", "587");

        // Authorized the Session object.
        Session mailSession = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM, PASS);
            }
        });

        try {
            Message message = new MimeMessage(mailSession);

            message.setFrom(new InternetAddress(FROM));

            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(recipientMail));

            message.setSubject(SUBJECT);
            message.setText(text);
            Transport.send(message);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
