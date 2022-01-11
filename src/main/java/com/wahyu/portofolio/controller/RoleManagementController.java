package com.wahyu.portofolio.controller;

import com.wahyu.portofolio.model.RoleManagement;
import com.wahyu.portofolio.service.RoleManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/role-management")
public class RoleManagementController {

    @Autowired
    private RoleManagementService roleManagementService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public RoleManagement save(@RequestBody RoleManagement roleManagement) {
        return roleManagementService.save(roleManagement);
    }

    @RequestMapping(value = "/find-all", method = RequestMethod.GET)
    public List<RoleManagement> findAll() {
        return roleManagementService.findAll();
    }

    @RequestMapping(value = "/find-by-id", method = RequestMethod.GET)
    public Optional<RoleManagement> findById(@RequestParam Integer roleId) {
        return roleManagementService.findById(roleId);
    }
}
