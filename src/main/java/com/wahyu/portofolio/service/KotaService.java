package com.wahyu.portofolio.service;

import com.wahyu.portofolio.dao.KotaDao;
import com.wahyu.portofolio.model.Kota;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KotaService {

    @Autowired
    private KotaDao kotaDao;

    public List<Kota> findAll() {
        return kotaDao.findAll();
    }

    public Kota save(Kota kota) {
        return kotaDao.save(kota);
    }

    public Optional<Kota> findById(Integer kdProvinsi) {
        return kotaDao.findById(kdProvinsi);
    }

    public void deleteById(Integer kdProvinsi){
        kotaDao.deleteById(kdProvinsi);
    }

    public Kota update(Kota kota) { return kotaDao.save(kota);}
}
