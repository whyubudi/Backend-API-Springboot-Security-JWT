package com.wahyu.portofolio.service;

import com.wahyu.portofolio.dao.RoleManagementDao;
import com.wahyu.portofolio.model.RoleManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleManagementService {

    @Autowired
    private RoleManagementDao roleManagementDao;

    public List<RoleManagement> findAll(){
        return roleManagementDao.findAll();
    }

    public RoleManagement save(RoleManagement roleManagement){
        return roleManagementDao.save(roleManagement);
    }

    public Optional<RoleManagement> findById(int roleId){
        return roleManagementDao.findById(roleId);
    }
}
