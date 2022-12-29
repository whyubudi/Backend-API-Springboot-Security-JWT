package com.wahyu.portofolio.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public class Default {

    @JsonIgnore
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @JsonIgnore
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;
}
