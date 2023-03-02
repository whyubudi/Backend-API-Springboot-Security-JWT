package com.wahyu.portofolio.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Where(clause = "isdeleted=false")
@Table(name = "ATM_LOCATION")
public class AtmLocation {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "MODIFIED_DATE")
    private Date modifiedDate;

    @Column(name = "MODIFIED_BY")
    private String modifiedBy;

    @Column(name = "ATM_MACHINE")
    private String atmMachine;

    @Column(name = "ATM_NAME")
    private String atmName;

    @Column(name = "ATM_ADDRESS")
    private String atmAddress;

    @Column(name = "LONGITUDE")
    private String longitude;

    @Column(name = "LATITUDE")
    private String latitude;

    @Column(name = "GOOGLE_MAP")
    private String googleMap;

    @Column(name = "ISDELETED")
    private boolean deleted;

    @Column(name = "STATUS")
    private boolean status;
}
