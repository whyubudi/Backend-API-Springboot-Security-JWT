package com.wahyu.portofolio.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "disable_alert")
public class DisableAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nip;
    @NotNull
    private String hpan;
    @JsonFormat(pattern="dd.MM.yyyy HH:mm:ss")
    private Timestamp modified;
}
