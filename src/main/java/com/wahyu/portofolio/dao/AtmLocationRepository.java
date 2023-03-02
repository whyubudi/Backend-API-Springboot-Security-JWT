package com.wahyu.portofolio.dao;

import com.wahyu.portofolio.model.AtmLocation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AtmLocationRepository extends JpaRepository<AtmLocation, Long> {

  @Query(value = "SELECT * FROM parameter_management_service.atm_location a WHERE" +
      " ( :subsLatitude <= a.latitude AND a.latitude <= :addLatitude ) AND ( :subsLongitude <= a.longitude AND a.longitude <= :addLongitude )", nativeQuery = true)
  List<AtmLocation> findNearestBranch(@Param("subsLatitude") String subsLatitude,
                                      @Param("addLatitude") String addLatitude, @Param("subsLongitude") String subsLongitude,
                                      @Param("addLongitude") String addLongitude);

}
