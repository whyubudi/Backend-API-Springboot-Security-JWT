package com.wahyu.portofolio.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Data
@Entity
@Table(name = "t_iss_alerts")
public class IssuerAlerts {

    @Id
    @Column(name = "ID")
    private Long id;
    @JsonSerialize(as = Date.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp ttime;
    private Integer investigationDecision;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID",referencedColumnName = "UTRNNO",insertable = false, updatable = false)
    private TransData transData;

//    @OneToMany( mappedBy = "issuerAlerts", orphanRemoval = true,
//            targetEntity = Memo.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<Memo> memo;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID",referencedColumnName = "TRANSACTION_ID",insertable = false, updatable = false)
    private UserStatusUpdate userStatusUpdate;



//    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
//    @JoinColumn(name = "ID",referencedColumnName = "TRANSACTION_ID",insertable = false, updatable = false)
//    private SettingNip settingNip;

//    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
//    @JoinColumn(name = "ID",referencedColumnName = "TRANSACTION_ID",insertable = false, updatable = false)
//    private Memo memo;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinTable(name = "memo",
//            joinColumns =
//                    { @JoinColumn(name = "TRANSACTION_ID", referencedColumnName = "ID") },
//            inverseJoinColumns =
//                    { @JoinColumn(name = "ID", referencedColumnName = "TRANSACTION_ID") })
//    private Memo memo;

//    @OneToMany( mappedBy = "issuerAlerts", orphanRemoval = true,
//            targetEntity = SettingNip.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<SettingNip> settingNip;

//    @OneToMany( mappedBy = "issuerAlerts", orphanRemoval = true,
//            targetEntity = TransData.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<TransData> transData;

}
