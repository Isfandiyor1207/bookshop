package epam.project.bookshop.service.impl;

import epam.project.bookshop.dto.UserDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;

public class SendEmailService {

    private static final Logger logger= LogManager.getLogger();
    private static final SendEmailService instance = new SendEmailService();
    private static String fromEmail;
    private static String password;
    private static String host;
    private static String smtpAuth;
    private static String smtpPort;
    private static String starttlsEnable;
    private static String connectionTimeout;
    private static String timeout;

    static {
        try (InputStream input = SendEmailService.class.getClassLoader().getResourceAsStream("/prop/config.properties")) {

            Properties prop = new Properties();

            prop.load(input);

            fromEmail = prop.getProperty("message.sender.email");
            password = prop.getProperty("message.sender.email.password");
            host = prop.getProperty("mail.smtp.host");
            smtpAuth = prop.getProperty("mail.smtp.auth");
            smtpPort = prop.getProperty("mail.smtp.port");
            starttlsEnable = prop.getProperty("mail.smtp.starttls.enable");
            connectionTimeout = prop.getProperty("mail.smtp.connectiontimeout");
            timeout = prop.getProperty("mail.smtp.timeout");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static SendEmailService getInstance() {
        return instance;
    }

    public String generatePassword() {

        int max = 999999;
        int min = 100000;

        Random random = new Random();
        int password = random.nextInt(max - min) + min;

        return String.format("%06d", password);
    }

    public boolean sendEmail(UserDto user) {
        boolean test = false;

        String toEmail = user.getEmail();

        try {

            // your host email smtp server details
            Properties props = new Properties();
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.auth", smtpAuth);
            props.put("mail.smtp.port", smtpPort);
            props.put("mail.smtp.starttls.enable", starttlsEnable);
            props.put("mail.smtp.connectiontimeout", connectionTimeout); // 60 seconds
            props.put("mail.smtp.timeout", timeout);

            //get session to authenticate the host email address and password
            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, password);
                }
            });

            //set email message details
            Message mess = new MimeMessage(session);

            //set from email address
            mess.setFrom(new InternetAddress(fromEmail));

            //set to email address or destination email address
            mess.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));

            //set email subject
            mess.setSubject("Email Verification Code");

            //set message text
            mess.setText("Please verify your account using this code: " + user.getCode());
            //send the message
            Transport.send(mess);

            test = true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return test;
    }
}
