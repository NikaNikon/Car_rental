package com.litovchenko.carsapp.service;

import com.twilio.Twilio;
import com.twilio.type.PhoneNumber;
import org.apache.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailSender {

    static final Logger LOGGER = Logger.getLogger(EmailSender.class);

    public static void sendEmail(String to){

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(
                                "07nikon@gmail.com","veronika1997");
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("07nikon@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            message.setSubject("Wheels car rental service");
            message.setText("CONGRATULATIONS!" +
                    "\n\n You successfully registered! Enjoy our cool cars.");

            Transport.send(message);

        } catch (MessagingException e) {
            LOGGER.error("Cannot send notification by email: " + e);
            throw new UserInputException(e);
        }
    }

}
