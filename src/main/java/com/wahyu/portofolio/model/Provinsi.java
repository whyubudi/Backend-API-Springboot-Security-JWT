package com.wahyu.portofolio.model;


import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "Provinsi")
@EntityListeners(AuditingEntityListener.class)
public class Provinsi {


    @Column(name = "name_provinsi", length = 50)
    private String provinsi;

    @Id
    @NotNull
    @GeneratedValue
    @Column(name = "kd_provinsi", nullable = false)
    private Integer kdPronvinsi;

}
