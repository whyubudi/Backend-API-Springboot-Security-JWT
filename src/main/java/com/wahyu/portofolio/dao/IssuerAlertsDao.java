package com.wahyu.portofolio.dao;

import com.wahyu.portofolio.model.IssuerAlerts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IssuerAlertsDao extends JpaRepository<IssuerAlerts, Long> {

}
