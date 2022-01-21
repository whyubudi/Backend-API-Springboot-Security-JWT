package com.wahyu.portofolio.service;

import com.wahyu.portofolio.dao.BackendAuditrailDao;
import com.wahyu.portofolio.model.BackendAuditrail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BackendAuditrailService {

    @Autowired
    BackendAuditrailDao backendAuditrailDao;

    public List<BackendAuditrail> findAll(){
        return backendAuditrailDao.findAll();
    }

    public BackendAuditrail save(BackendAuditrail backendAuditrail){
        return backendAuditrailDao.save(backendAuditrail);
    }
}
