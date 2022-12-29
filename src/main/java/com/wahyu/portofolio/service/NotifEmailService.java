package com.wahyu.portofolio.service;


import com.wahyu.portofolio.dto.email.EmailRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Service
@Slf4j
public class NotifEmailService {

    @Value("${spring.mail.username}")
    private String sender;

    @Value("${spring.mail.host}")
    private String mailHost;
    @Value("${spring.mail.password}")
    private String mailpass;

    @Value("${spring.mail.port}")
    private String hostPort;

    public Map<String,Object> sendMail(EmailRequest details) {

        log.info("Request {} ", details);
        Map<String,Object> response = new HashMap<>();
        Properties properties = System.getProperties();

        properties.put("mail.smtp.host", mailHost);
        properties.put("mail.smtp.port", hostPort);
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable","true");

        Session session = Session.getInstance(properties, new Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sender, mailpass);
            }

        });
        session.setDebug(true);
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sender));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(details.getRecipient()));
            message.setSubject(details.getSubject());
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(details.getMsgText());
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);

            Transport.send(message);
            response.put("rc", "00");
            response.put("rd", "Success");

        }catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            response.put("rc", "01");
            response.put("rd", "Failed");
        }

        log.info("Response {} ",response);
        return response;
    }
}

