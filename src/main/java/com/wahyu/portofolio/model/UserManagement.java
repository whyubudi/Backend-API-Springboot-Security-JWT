package com.wahyu.portofolio.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import com.wahyu.portofolio.dto.Default;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "USER_MANAGEMENT")
@EntityListeners(AuditingEntityListener.class)
public class UserManagement extends Default {

    @Id
    @NotNull
    @Column(name = "USERNAME", length = 50, nullable = false)
    private String username;

    @Column(name = "FIRSTNAME", length = 120, nullable = false)
    private String firstName;

    @Column(name = "LASTNAME", length = 120, nullable = false)
    private String lastName;

    @Column(name = "ADDRESS", nullable = false)
    private String address;

    @Column(name = "PASSWORD", length = 120, nullable = false)
    private String password;

    @Column(name = "ROLEID")
    private int roleId;

    @Column(name = "EMAIL")
    private String email;


    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "ROLEID", insertable = false, updatable = false)
    private RoleManagement roleManagement;
}
