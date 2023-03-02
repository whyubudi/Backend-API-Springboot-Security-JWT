package com.wahyu.portofolio.controller;

import com.wahyu.portofolio.constant.NotificationStatus;
import com.wahyu.portofolio.dao.NotificationRepo;
import com.wahyu.portofolio.dao.SmsResponseCodeRepo;
import com.wahyu.portofolio.dto.email.EmailRequest;
import com.wahyu.portofolio.dto.sms.SmsRequest;
import com.wahyu.portofolio.dto.whatsapp.WhatsAppRequest;
import com.wahyu.portofolio.model.Notification;
import com.wahyu.portofolio.model.SmsResponseCode;
import com.wahyu.portofolio.service.EmailService;
import com.wahyu.portofolio.service.WhatsAppService;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/notification")
public class SendNotification {

    @Autowired
    private EmailService notifEmailService;

    @Autowired
    private WhatsAppService whatsAppService;

    @Autowired
    private NotificationRepo notificationRepo;

    @Autowired
    private SmsResponseCodeRepo smsResponseCodeRepo;

    @CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
    @RequestMapping(value = "/sendsms", method = RequestMethod.POST)
    public Map<String, Object> sendSMS(@RequestBody SmsRequest notif) throws Exception {
        try {
            Map<String, Object> resp = new HashMap<>();
            String url = "http://" + notif.getContact() + "&sms=" + notif.getMessage() + "&ref_transaction= &engine=";
            log.info("sms message : {}", url);
            MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
            converter.setSupportedMediaTypes(Arrays.asList(MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON));
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(1, converter);
            ResponseEntity<SmsResponseCode> response = restTemplate.getForEntity(url, SmsResponseCode.class);
            SmsResponseCode object = response.getBody();
            log.info("sms message response: {}", response);
            assert object != null;
            var smsResponseCode = smsResponseCodeRepo.findAllByResult(object.getResult());

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date1 = sdf.format(new Date());

            Notification notification = new Notification();
            if (smsResponseCode.getResult() == true) {
                notification.setUser(notif.getUser());
                notification.setPhoneNumber(notif.getContact());
                notification.setMessage(notif.getMessage());
                notification.setDateSent(Timestamp.valueOf(date1));
                notification.setStatus(String.valueOf(NotificationStatus.SUCCESS));
                notification.setNotifType("sms");
                notificationRepo.save(notification);
            }else {
                notification.setUser(notif.getUser());
                notification.setPhoneNumber(notif.getContact());
                notification.setMessage(notif.getMessage());
                notification.setDateSent(Timestamp.valueOf(date1));
                notification.setStatus(String.valueOf(NotificationStatus.FAILED));
                notification.setNotifType("sms");
                notificationRepo.save(notification);
            }
            resp.put("result", smsResponseCode.getResult());
            resp.put("res_dsc", smsResponseCode.getResDsc());
            resp.put("reff_number", smsResponseCode.getReffNumber());
            log.info("response : {} ", resp);
            return resp;
        } catch (Exception e) {
            log.error("Failed send sms : ", e);
            throw e;
        }
    }


    @CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
    @PostMapping("/sendmail")
    public Map<String, Object> sendMail(@RequestBody EmailRequest details) {
        return notifEmailService.sendMail(details);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
    @PostMapping("/sendwhatsapp")
    public Map<String, Object> sendWhatsApp(@RequestBody WhatsAppRequest whatsAppRequest) throws Exception {
        return whatsAppService.sendWhatsApp(whatsAppRequest);
    }
}
