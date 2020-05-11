/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nura.mail;

import constants.Constants;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


/**
 *
 * @author Arun kumar
 */
public class SendMail implements Constants {

    public static void main(String receiver, String msg, String header) {

        final String username = Constants.MAIL_ID;
        final String password = Constants.MAIL_PWD;

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(receiver));
            message.setSubject(header);
            message.setText(msg);
            Transport.send(message);
            System.out.println("Mail has been sent");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    
     public static void main(String[] args) {
        SendMail.main("aprodigalboy@gmail.com", "Test mail", "test");
    }
}
