package org.unibl.etf.models;

import org.unibl.etf.services.MailService;

public class EmailTask implements Runnable{
    private final String to;
    private final String ticket;
    private final String notification;
    private final MailService mailService;

    public EmailTask(String to, String ticket, String notification, MailService mailService) {
        this.to = to;
        this.ticket = ticket;
        this.notification = notification;
        this.mailService = mailService;
    }

    @Override
    public void run() {
        System.out.println("Email task started. Sending mail to: " + to + " with message: " + notification);
        mailService.sendMail(to, ticket + "\n\n" +  notification);
    }
}
