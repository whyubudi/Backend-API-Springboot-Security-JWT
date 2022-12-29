package com.wahyu.portofolio.controller;

import com.wahyu.portofolio.dto.email.EmailRequest;
import com.wahyu.portofolio.dto.sms.SmsRequest;
import com.wahyu.portofolio.dto.whatsapp.WhatsAppRequest;
import com.wahyu.portofolio.service.NotifEmailService;
import com.wahyu.portofolio.service.WhatsAppService;
import com.wahyu.portofolio.util.AbstractManagedBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/notification")
public class SendNotification extends AbstractManagedBean {

    @Autowired
    private NotifEmailService notifEmailService;

    @Autowired
    private WhatsAppService whatsAppService;


    @CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
    @RequestMapping(value = "/send-sms", method = RequestMethod.POST)
    public Map<String, Object> sendSMS(@RequestBody SmsRequest notif) {

        Map<String, Object> resp = new HashMap<>();
        String url = "http://10.14.18.177:8089/notif?nohandphone=" + notif.getContact() + "&sms=" + notif.getMessage() + "&ref_transaction=12322566959&engine=DETIKCREDIT";
        System.out.println(url);
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Arrays.asList(MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON));
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(1, converter);
        ResponseEntity<Object> response = restTemplate.getForEntity(url, Object.class);
        System.out.println(response);

        if (response != null) {
            if (response.getStatusCode() == HttpStatus.OK) {
                resp.put("code","00");
                resp.put("response","success");
                System.out.println("Sms Telah dikirim");
            } else {
                resp.put("code","01");
                resp.put("response","failed");
            }
        }
        return resp;
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
    @PostMapping("/sendmail")
    public Map<String, Object> sendMail(@RequestBody EmailRequest details) {
        return notifEmailService.sendMail(details);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
    @PostMapping("/sendwhatsapp")
    public Map<String, Object> sendWhatsApp(@RequestBody WhatsAppRequest whatsAppRequest) throws Exception{
        return whatsAppService.sendWhatsApp(whatsAppRequest);
    }
}
