package com.wahyu.portofolio.dao;

import com.wahyu.portofolio.model.UserManagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserManagementDao extends JpaRepository<UserManagement, String> {

    @Query("SELECT um "
            + "FROM UserManagement um WHERE lower(um.email) =:email")
    UserManagement findByEmail(@Param("email") String email);

}
