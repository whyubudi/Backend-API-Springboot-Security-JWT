package com.wahyu.portofolio.controller;

import com.wahyu.portofolio.model.BackendAuditrail;
import com.wahyu.portofolio.service.BackendAuditrailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/auditrail")
public class BackendAuditrailController {

    @Autowired
    BackendAuditrailService backendAuditrailService;

    @RequestMapping(value = "/find-all", method = RequestMethod.GET)
    public List<BackendAuditrail> findAll() {
        return backendAuditrailService.findAll();
    }
}
