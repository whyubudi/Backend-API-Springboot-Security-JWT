package com.wahyu.portofolio.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "kota")
@EntityListeners(AuditingEntityListener.class)
public class Kota {

    @Column(name = "kota", length = 50)
    private String kota;

    @Id
    @Column(name = "kd_provinsi")
    private Integer kdProvinsi;

    @ManyToOne
    @JoinColumn(name = "kd_provinsi", insertable = false, updatable = false)
    private Provinsi provinsi;
}
