package com.wahyu.portofolio.dao;

import com.wahyu.portofolio.model.WaResponseCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WaResponseCodeDao extends JpaRepository<WaResponseCode, Integer> {
   WaResponseCode findAllByResponseCode(Integer responsecode);
}
