package com.wahyu.portofolio.dto.search;

import lombok.Data;

@Data
public class SearchRequest {

    private Integer page;
    private Integer size;
    private String hpan;
    private String mcc;
    private String merchantId;
    private String investigationDecision;
    private String startDate;
    private String endDate;
}
