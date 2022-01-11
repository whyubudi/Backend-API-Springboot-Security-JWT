package com.wahyu.portofolio.controller;

import com.wahyu.portofolio.model.Kota;
import com.wahyu.portofolio.service.KotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/kota")
public class KotaController {

    @Autowired
    private KotaService kotaService;


    @PostMapping("/save")
    public Kota save(@RequestBody Kota kota) {
        return kotaService.save(kota);
    }

    @GetMapping("/find-all")
    public List<Kota> findAll(){
        return kotaService.findAll();
    }

    @GetMapping("/find-by-id")
    public Optional<Kota> findById(@RequestParam Integer kdProvinsi) {
        return kotaService.findById(kdProvinsi);
    }

    @DeleteMapping("/delete-by-id")
    public String deleteById(@RequestParam Integer kdProvinsi) {
        kotaService.deleteById(kdProvinsi);
        return "Delete Berhasil";
    }

    @PutMapping("/update")
    public String update(@RequestBody Kota kota){
        kotaService.update(kota);
        return "Data berhasil di update";
        }
}
