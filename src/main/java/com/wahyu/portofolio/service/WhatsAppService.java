package com.wahyu.portofolio.service;

import com.wahyu.portofolio.constant.NotificationStatus;
import com.wahyu.portofolio.dao.NotificationRepo;
import com.wahyu.portofolio.dao.WaResponseCodeRepo;
import com.wahyu.portofolio.dto.whatsapp.WhatsAppRequest;
import com.wahyu.portofolio.model.Notification;
import com.wahyu.portofolio.model.WaResponseCode;
import com.wahyu.portofolio.util.PhoneNumberUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class WhatsAppService {

    @Value("${url.waGatewayJatis}")
    private String waGatewayJatisUrl;
    @Value("${userIdWaGateway.notification}")
    private String userIdWaGatewayNotification;
    @Value("${passwordWaGateway.notification}")
    private String passwordWaGatewayNotification;
    @Value("${waSender.notification}")
    private String waSenderNotification;
    @Value("${divisionWaGateway.notification}")
    private String divisionWaGatewayNotification;
    @Value("${channelWaGateway.notification}")
    private String channelWaGatewayNotification;
    @Value("${waBatchName.notification}")
    private String waBatchNameNotification;
    @Value("${waUploadBy.notification}")
    private String waUploadByNotification;
    @Value("${wa.gateway.type}")
    private String waGatewayProvider;

    @Value("${proxy.host}")
    private String hostProxy;
    @Value("${proxy.port}")
    private int portProxy;

    @Autowired
    private WaResponseCodeRepo waResponseCodeRepo;

    @Autowired
    private NotificationRepo notificationRepo;


    public Map<String, Object> sendWhatsApp(WhatsAppRequest whatsAppRequest) throws Exception {
        log.info("start send to jatis");
        log.info("Request WA : {}", whatsAppRequest);
        Map<String, Object> resp = null;
        try {
            if (StringUtils.isEmpty(whatsAppRequest.getPhoneNumber())) {
                log.info("phone number is empty");
            } else {
                String internationalPhoneNumber = PhoneNumberUtil.constructInternationalPhoneNumber(whatsAppRequest.getPhoneNumber());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
                String date = simpleDateFormat.format(new Date());
                String message = whatsAppRequest.getMessage();
                String url = waGatewayJatisUrl +
                            "/index.ashx?" +
                            "userid=" + userIdWaGatewayNotification +
                            "&password=" + passwordWaGatewayNotification +
                            "&msisdn=" + internationalPhoneNumber +
                            "&message={message}" +
                            "&sender=" + waSenderNotification +
                            "&division="  + divisionWaGatewayNotification +
                            "&batchname=" + date + waBatchNameNotification +
                            "&uploadby=" + waUploadByNotification +
                            "&channel=" + channelWaGatewayNotification +
                            "&type=" + waGatewayProvider;

                log.info("jatis message : {}", url);
                Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(hostProxy,portProxy));
                SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
                requestFactory.setProxy(proxy);
                RestTemplate restTemplate = new RestTemplate();
                String response = restTemplate.getForObject(url, String.class, message);
                log.info("response : {} ", response);

                String rc = response.split("&")[0].split("=")[1];
                WaResponseCode waResponseCode = waResponseCodeRepo.findAllByResponseCode(Integer.valueOf(rc));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String date1 = sdf.format(new Date());


                Notification notification = new Notification();
                if (waResponseCode.getResponseStatus().equals(1)) {
                    notification.setUser(whatsAppRequest.getUser());
                    notification.setPhoneNumber(whatsAppRequest.getPhoneNumber());
                    notification.setMessage(whatsAppRequest.getMessage());
                    notification.setDateSent(Timestamp.valueOf(date1));
                    notification.setStatus(String.valueOf(NotificationStatus.SUCCESS));
                    notification.setNotifType("whatsapp");
                    notificationRepo.save(notification);
                }else {
                    notification.setUser(whatsAppRequest.getUser());
                    notification.setPhoneNumber(whatsAppRequest.getPhoneNumber());
                    notification.setMessage(whatsAppRequest.getMessage());
                    notification.setDateSent(Timestamp.valueOf(date1));
                    notification.setStatus(String.valueOf(NotificationStatus.FAILED));
                    notification.setNotifType("whatsapp");
                    notificationRepo.save(notification);
                }
                resp = new HashMap<>();
                resp.put("responseCode", waResponseCode.getResponseCode());
                resp.put("responseStatus", waResponseCode.getResponseStatus());
            }
        } catch (Exception e) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date1 = sdf.format(new Date());
            Notification notification = new Notification();
            notification.setUser(whatsAppRequest.getUser());
            notification.setPhoneNumber(whatsAppRequest.getPhoneNumber());
            notification.setMessage(whatsAppRequest.getMessage());
            notification.setDateSent(Timestamp.valueOf(date1));
            notification.setStatus(String.valueOf(NotificationStatus.FAILED));
            notification.setNotifType("whatsapp");
            notificationRepo.save(notification);
            log.error("Failed send wa : ", e);
            throw e;
        }

        return resp;
    }
}

