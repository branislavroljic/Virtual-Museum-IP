package org.unibl.etf.services;

import javax.mail.MessagingException;

public interface MailService {

    void sendTicket(String to, String ticketPath) throws MessagingException;

    void sendMail(String to,String text);
}
