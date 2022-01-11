package com.wahyu.portofolio.controller;

import com.wahyu.portofolio.model.Provinsi;
import com.wahyu.portofolio.service.ProvinsiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("provinsi")
public class ProvinsiController {

    @Autowired
    ProvinsiService provinsiService;

    @PostMapping(value = "/save")
    public Provinsi save(@RequestBody Provinsi provinsi ){
        return provinsiService.save(provinsi);
    }

    @GetMapping("/find-all")
    public List<Provinsi> findAll() {
        return provinsiService.findAll();
    }

    @GetMapping("/find-by-id")
    public Optional<Provinsi> findById(@RequestParam Integer kdProvinsi) {
        return provinsiService.findById(kdProvinsi);
    }

    @DeleteMapping("/delete-by-id")
    public String deleteById(@RequestParam Integer kdProvinsi) {
        provinsiService.deleteById(kdProvinsi);
        return "Delete Berhasil";
    }
    @PutMapping("/update")
    public String update(@RequestBody Provinsi provinsi){
       provinsiService.update(provinsi);
        return "Data berhasil di update";
    }
}
