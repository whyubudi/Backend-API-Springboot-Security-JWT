package com.wahyu.portofolio.dto.audit;

import lombok.Data;


@Data
public class AuditEmail {

    private String cardNumber;
    private Integer page;
    private Integer size;
}
