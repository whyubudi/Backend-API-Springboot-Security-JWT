package com.wahyu.portofolio.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "t_iss_alerts")
public class IssuerAlerts {

    @Id
    @Column(name = "ID")
    private Long id;
    @JsonFormat(pattern="dd.MM.yyyy HH:mm:ss")
    private Timestamp ttime;
    private String investigationDecision;

}
