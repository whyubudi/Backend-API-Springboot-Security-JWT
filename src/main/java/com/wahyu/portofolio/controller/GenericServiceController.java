package com.wahyu.portofolio.controller;

import com.wahyu.portofolio.model.Constants;
import com.wahyu.portofolio.model.requestbody.DynamicQuery;
import com.wahyu.portofolio.model.responsebody.ErrorCode;
import com.wahyu.portofolio.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/generic-service")
public class GenericServiceController {

    @Autowired
    GenericService genericService;

    ErrorCode errorCode;

    @PostMapping(path = "/find-by-query")
    public ResponseEntity<?> findUsingQuery(@RequestBody DynamicQuery dynamicQuery){

        try{
            List<Map<String,Object>> abc = genericService.findUsingJDBC(dynamicQuery.getQuery());
            return ResponseEntity.ok(abc);
        }catch (NullPointerException e){
            errorCode.setCode(Constants.QUERY_UNDEFINE[0]);
            errorCode.setMessage(Constants.QUERY_UNDEFINE[1]);
            return ResponseEntity.badRequest().body(errorCode);
        }
    }
}
