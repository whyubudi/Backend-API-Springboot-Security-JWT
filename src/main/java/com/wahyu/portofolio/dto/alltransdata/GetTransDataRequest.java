package com.wahyu.portofolio.dto.alltransdata;

import lombok.Data;

@Data
public class GetTransDataRequest {

    private Integer page;
    private Integer size;
    private String transactionId;
    private String hpan;
    private String mcc;
    private String merchantId;
    private String issuerInstitutionId;
    private String acquirerInstitutionId;
    private String amount_1;
    private String transactionType;
    private String terminalId;
    private String addressName;
    private String addressCity;
    private String countryCode;
    private String respCode;
    private String riskValue;
    private String ruleId;
    private Integer investigationDecision;
    private String nip;
    private String startDate;
    private String endDate;
}
