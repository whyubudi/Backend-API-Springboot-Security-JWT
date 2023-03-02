package com.wahyu.portofolio.dao;

import com.wahyu.portofolio.model.WaResponseCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WaResponseCodeRepo extends JpaRepository<WaResponseCode, Integer> {
   WaResponseCode findAllByResponseCode(Integer responsecode);
}
