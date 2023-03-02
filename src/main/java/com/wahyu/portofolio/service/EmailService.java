package com.wahyu.portofolio.service;

import com.wahyu.portofolio.constant.NotificationStatus;
import com.wahyu.portofolio.dao.NotificationRepo;
import com.wahyu.portofolio.dto.email.EmailRequest;
import com.wahyu.portofolio.model.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Service
@Slf4j
public class EmailService {

    @Value("${spring.mail.username}")
    private String sender;

    @Value("${spring.mail.host}")
    private String mailHost;
    @Value("${spring.mail.password}")
    private String mailpass;

    @Value("${spring.mail.port}")
    private String hostPort;

    @Autowired
    private NotificationRepo notificationRepo;

    public Map<String, Object> sendMail(EmailRequest details) {

        log.info("Request {} ", details);
        Map<String, Object> response = new HashMap<>();
        Properties properties = System.getProperties();

        properties.put("mail.smtp.host", mailHost);
        properties.put("mail.smtp.port", hostPort);
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

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

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date1 = sdf.format(new Date());

            Notification notification = new Notification();
            notification.setRecipient(details.getRecipient());
            notification.setSubject(details.getSubject());
            notification.setUser(details.getUser());
            notification.setRecipient(details.getRecipient());
            notification.setMessage(details.getMsgText());
            notification.setDateSent(Timestamp.valueOf(date1));
            notification.setStatus(String.valueOf(NotificationStatus.SUCCESS));
            notification.setNotifType("email");
            notificationRepo.save(notification);


            Transport.send(message);
            response.put("rc", "00");
            response.put("rd", "Success");

        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            response.put("rc", "01");
            response.put("rd", "Failed");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date1 = sdf.format(new Date());
            Notification notification = new Notification();
            notification.setRecipient(details.getRecipient());
            notification.setSubject(details.getSubject());
            notification.setUser(details.getUser());
            notification.setRecipient(details.getRecipient());
            notification.setMessage(details.getMsgText());
            notification.setDateSent(Timestamp.valueOf(date1));
            notification.setStatus(String.valueOf(NotificationStatus.FAILED));
            notificationRepo.save(notification);
        }

        log.info("Response {} ", response);
        return response;
    }
}

