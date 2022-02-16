package com.wahyu.portofolio.model;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "ROLE_MANAGEMENT")
@EntityListeners(AuditingEntityListener.class)
public class RoleManagement {

    @Id
    @NotNull
    @Column(name = "ROLEID", nullable = false)
    private int roleId;


}
