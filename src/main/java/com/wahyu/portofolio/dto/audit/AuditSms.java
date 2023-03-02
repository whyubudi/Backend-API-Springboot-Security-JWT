package com.wahyu.portofolio.dto.audit;

import lombok.Data;


@Data
public class AuditSms {

    private String cardNumber;
    private Integer page;
    private Integer size;
}
