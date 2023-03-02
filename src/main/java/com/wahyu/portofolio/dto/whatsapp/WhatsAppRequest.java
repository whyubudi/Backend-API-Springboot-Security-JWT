package com.wahyu.portofolio.dto.whatsapp;

import lombok.Data;

@Data
public class WhatsAppRequest {

    private String phoneNumber;
    private String message;
    private String user;

}
