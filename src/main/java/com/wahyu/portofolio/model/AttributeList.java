package com.wahyu.portofolio.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "t_attribute_to_list_value")
public class AttributeList {

    @Id
    private String listValue;
    private String description;
}
