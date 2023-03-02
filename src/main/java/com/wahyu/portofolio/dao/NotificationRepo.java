package com.wahyu.portofolio.dao;

import com.wahyu.portofolio.model.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface NotificationRepo extends JpaRepository<Notification,String> {

    Page<Notification> findByNotifType(String notiftype, Pageable pageable);

}
