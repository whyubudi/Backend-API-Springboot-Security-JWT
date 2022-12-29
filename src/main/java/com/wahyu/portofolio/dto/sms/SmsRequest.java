package com.wahyu.portofolio.dto.sms;

import lombok.Data;

@Data
public class SmsRequest {

    private String contact;
    private String message;
}
