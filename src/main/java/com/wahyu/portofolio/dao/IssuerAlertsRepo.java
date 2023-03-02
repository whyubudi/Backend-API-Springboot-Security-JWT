package com.wahyu.portofolio.dao;

import com.wahyu.portofolio.model.IssuerAlerts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface IssuerAlertsRepo extends JpaRepository<IssuerAlerts, Long>, JpaSpecificationExecutor<IssuerAlerts> {

    Optional<IssuerAlerts> findByIdGreaterThanEqual(Long id);
    List<IssuerAlerts> findByIdIn(List<Long> transactionid);
}
