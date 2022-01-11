package com.wahyu.portofolio.service;

import com.wahyu.portofolio.dao.ProvinsiDao;
import com.wahyu.portofolio.model.Provinsi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProvinsiService {

    @Autowired
    ProvinsiDao provinsiDao;

    public Provinsi save(Provinsi provinsi){
        return provinsiDao.save(provinsi);
    }

    public List<Provinsi> findAll() {
        return provinsiDao.findAll();
    }

    public Optional<Provinsi> findById(Integer kdProvinsi){
        return provinsiDao.findById(kdProvinsi);
    }

    public void deleteById(Integer kdProvinsi){
        provinsiDao.deleteById(kdProvinsi);
    }

    public Provinsi update(Provinsi provinsi){ return provinsiDao.save(provinsi);}


}
