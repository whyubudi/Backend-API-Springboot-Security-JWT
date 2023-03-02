package com.wahyu.portofolio.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "smsresponsecode")
public class SmsResponseCode {

    @Id
    @Column(name = "result")
    private Boolean result;

    @Column(name = "res_dsc")
    private String resDsc;

    @Column(name = "reff_number")
    private String reffNumber;
}
