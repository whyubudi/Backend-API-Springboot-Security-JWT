package com.wahyu.portofolio.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "WARESPONSECODE")
public class WaResponseCode {

    @Id
    @Column(name = "RESPONSECODE")
    private Integer responseCode;

    @Column(name = "RESPONSESTATUS")
    private String responseStatus;
}
