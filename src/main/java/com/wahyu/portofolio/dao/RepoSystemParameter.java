package com.wahyu.portofolio.dao;

import com.wahyu.portofolio.model.SystemParameter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoSystemParameter extends JpaRepository<SystemParameter,String> {

    SystemParameter findAllByParameter(String params);
}
