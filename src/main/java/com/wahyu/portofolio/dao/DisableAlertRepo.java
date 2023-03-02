package com.wahyu.portofolio.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface DisableAlertRepo extends JpaRepository<DisableAlert, Long> {

    List<DisableAlert> findByHpan(List<String> hpan);
    List<DisableAlert> findAllByHpan(String hpan);


//    List<DisableAlert> findAllByHpan(String hpan);

//    List<DisableAlert> findByHpanIgnoreCase(String hpan);

//    @Modifying
//    @Query(value = "DELETE FROM DisableAlert a WHERE a.hpan =:hpan")
//    void deleteAll(String hpan);

    void removeByHpan(String hpan);
    void deleteAllByHpan(String hpan);

}
