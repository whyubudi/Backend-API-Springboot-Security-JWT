package com.wahyu.portofolio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DemografiData {

    private String cardNum;
    private String cardIssDate;
    private String cardExpDate;
    private String cardStatus;
    private String cardCategory;
    private String agentNum;
    private String agentName;
    private String productNum;
    private String productName;
    private String custNum;
    private String custName;
    private String custNationality;
    private String custBirthDate;
    private String custSex;
    private String acctNum;
    private String acctStatus;
    private String idType;
    private String idNum;
    private String idIssDate;
    private String idExpDate;
    private String idDesc;
    private String addrType;
    private String addrRegion;
    private String addrCity;
    private String addrStreet;
    private String addrPostCode;
    private String contactMobile;
    private String contactEmail;
    private String sumLimit;
    private String blockAccount;
    private String blockCard;
}
