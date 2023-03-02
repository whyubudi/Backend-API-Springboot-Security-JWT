package com.wahyu.portofolio.dao;

import com.wahyu.portofolio.model.SmsResponseCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SmsResponseCodeRepo extends JpaRepository<SmsResponseCode, Boolean> {

    SmsResponseCode findAllByResult (Boolean result);
}
