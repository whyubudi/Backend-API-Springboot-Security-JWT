package com.wahyu.portofolio.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "fds_system_parameters")
public class SystemParameter {

    @Id
    private String parameter;
    private String value;
}
