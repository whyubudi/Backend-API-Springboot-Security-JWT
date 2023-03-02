package com.wahyu.portofolio.controller;

import com.wahyu.portofolio.dto.atmlocation.GetAtmLocationRq;
import com.wahyu.portofolio.dto.atmlocation.GetAtmLocationRs;
import com.wahyu.portofolio.service.AtmLocationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api
@RestController
public class AtmLocationController {

    @Autowired
    private AtmLocationService atmLocationService;

    @ApiOperation("Get Atm Location by Latitude and Longitude")
    @PostMapping("/getatmbylocation")
    public GetAtmLocationRs getBranchByLocation(@RequestBody GetAtmLocationRq request) {
        return atmLocationService.getAtmByLocation(request);
    }
}
