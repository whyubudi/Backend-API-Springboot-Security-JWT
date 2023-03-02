package com.wahyu.portofolio.controller;

import com.wahyu.portofolio.dao.NotificationRepo;
import com.wahyu.portofolio.dto.audit.NotificationDto;
import com.wahyu.portofolio.dto.audit.NotificationRequest;
import com.wahyu.portofolio.dto.audit.NotificationResponse;
import com.wahyu.portofolio.exeption.ParameterNotFoundException;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@Slf4j
@RequestMapping("/audit")
public class AuditTrailNotificationController {

    @Autowired
    private NotificationRepo notificationRepo;


    @CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
    @PostMapping("/getalldatanotif/{notifType}")
    public NotificationResponse getAllDataNotif(@PathVariable String notifType, @RequestBody NotificationRequest request) {
        try {
            log.info("request all notification : {} ", request);
            int actualPage = request.getPage() - 1;
            Pageable pageable = PageRequest.of(actualPage, request.getSize(), Sort.by("dateSent").descending());
            List<NotificationDto> listdto = new ArrayList<>();
            var byNotif = notificationRepo.findByNotifType(notifType, pageable);
            log.info("bynotif : {} ", byNotif);


            if (notifType.equalsIgnoreCase("sms")) {
                listdto = byNotif.stream().map(value -> NotificationDto.builder()
                        .id(value.getId())
                        .dateSent(value.getDateSent())
                        .notifType(value.getNotifType())
                        .user(value.getUser())
                        .message(value.getMessage())
                        .status(value.getStatus())
                        .phoneNumber(value.getPhoneNumber())
                        .build()).collect(Collectors.toList());
            } else if (notifType.equalsIgnoreCase("email")) {
                listdto = byNotif.stream().map(value -> NotificationDto.builder()
                        .id(value.getId())
                        .notifType(value.getNotifType())
                        .dateSent(value.getDateSent())
                        .user(value.getUser())
                        .recipient(value.getRecipient())
                        .subject(value.getSubject())
                        .message(value.getMessage())
                        .status(value.getStatus())
                        .build()).collect(Collectors.toList());
            } else if (notifType.equalsIgnoreCase("whatsapp")) {
                listdto = byNotif.stream().map(value -> NotificationDto.builder()
                        .id(value.getId())
                        .notifType(value.getNotifType())
                        .dateSent(value.getDateSent())
                        .phoneNumber(value.getPhoneNumber())
                        .user(value.getUser())
                        .message(value.getMessage())
                        .status(value.getStatus())
                        .build()).collect(Collectors.toList());
            } else {
                throw new ParameterNotFoundException("Notification Not Found");
            }

            return NotificationResponse.builder()
                    .notificationDtoList(listdto)
                    .totalPages(byNotif.getTotalPages())
                    .totalElements(byNotif.getTotalElements())
                    .numberOfElements(byNotif.getNumberOfElements())
                    .pageNumber(byNotif.getNumber())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
