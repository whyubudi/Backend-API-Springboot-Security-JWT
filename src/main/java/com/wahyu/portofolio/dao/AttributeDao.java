package com.wahyu.portofolio.dao;

import com.wahyu.portofolio.model.AttributeList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface AttributeDao extends JpaRepository<AttributeList, String> {

    AttributeList findAllByListValue(String mcc);
}
