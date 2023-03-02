package com.wahyu.portofolio.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "user_status_update")
public class UserStatusUpdate {

    @Id
    @Column(name = "TRANSACTION_ID")
    private Long transactionId;
    private String nip;

}
