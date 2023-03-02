package com.wahyu.portofolio.dao;

import com.wahyu.portofolio.model.SystemParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemParameterRepository extends JpaRepository<SystemParameter, Long> {

    SystemParameter findByModuleAndName(String module, String name);
}
