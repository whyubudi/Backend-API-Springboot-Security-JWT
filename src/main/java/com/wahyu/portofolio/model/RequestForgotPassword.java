package com.wahyu.portofolio.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "REQUEST_FORGOT_PASSWORD")
@EntityListeners(AuditingEntityListener.class)
public class RequestForgotPassword extends Default{

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "REQUEST_ID", nullable = false)
    private String reqId;

    @Column(name = "USERNAME", length = 50, nullable = false)
    private String username;

    @Column(name = "PASSWORD", length = 120, nullable = false)
    private String password;

    @Column(name = "STATUS")
    private int status;
}
