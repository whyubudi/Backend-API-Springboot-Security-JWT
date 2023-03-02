package com.wahyu.portofolio.service;

import com.wahyu.portofolio.dao.AtmLocationRepository;
import com.wahyu.portofolio.dao.SystemParameterRepository;
import com.wahyu.portofolio.dto.atmlocation.AtmLocationDto;
import com.wahyu.portofolio.dto.atmlocation.GetAtmLocationRq;
import com.wahyu.portofolio.dto.atmlocation.GetAtmLocationRs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AtmLocationService {


    @Autowired
    private SystemParameterRepository systemParameterRepository;

    @Autowired
    private AtmLocationRepository atmLocationRepository;

    private static float calculateDistanceInMeter(Double latitude, Double longitude, Double destLatitude, Double destLongitude) {
        double earthRadius = 6371000.0D;
        double dLat = Math.toRadians(destLatitude - latitude);
        double dLng = Math.toRadians(destLongitude - longitude);
        double a = Math.sin(dLat / 2.0D) * Math.sin(dLat / 2.0D) + Math.cos(Math.toRadians(latitude)) * Math.cos(Math.toRadians(destLatitude)) * Math.sin(dLng / 2.0D) * Math.sin(dLng / 2.0D);
        double c = 2.0D * Math.atan2(Math.sqrt(a), Math.sqrt(1.0D - a));
        return (float)Math.ceil(earthRadius * c);
    }

    public GetAtmLocationRs getAtmByLocation(GetAtmLocationRq request) {
        try {

            Double tmpParam,subsLat ,addLat,subsLong,addLong;
            var radiusValue = systemParameterRepository.findByModuleAndName("ATM_LOCATION", "RADIUS");
            tmpParam = ObjectUtils.isEmpty(radiusValue) ? Double.valueOf("0.03") : Double.valueOf(radiusValue.getValue());
            subsLat = request.getLatitude() - tmpParam;
            addLat = request.getLatitude() + tmpParam;
            subsLong = request.getLongitude() - tmpParam;
            addLong = request.getLongitude() + tmpParam;

            var nearLocations = atmLocationRepository.findNearestBranch(String.valueOf(addLat),
                    String.valueOf(subsLat), String.valueOf(subsLong), String.valueOf(addLong));

            if(!ObjectUtils.isEmpty(nearLocations)){
                List<AtmLocationDto> dtoList = nearLocations.stream().map(data ->
                        AtmLocationDto.builder()
                                .atmMachine(data.getAtmMachine())
                                .atmName(data.getAtmName())
                                .atmAddress(data.getAtmAddress())
                                .googleMap(data.getGoogleMap())
                                .latitude(data.getLatitude())
                                .longitude(data.getLongitude())
                                .distance(calculateDistanceInMeter(request.getLatitude(), request.getLongitude(), Double.valueOf(data.getLatitude()), Double.valueOf(data.getLongitude())))
                                .build()
                ).collect(Collectors.toList());
                List<AtmLocationDto> sortedListByNearestLocation = dtoList.stream().sorted(Comparator.comparing(AtmLocationDto::getDistance)).collect(Collectors.toList());
                return GetAtmLocationRs.builder().atmLocations(sortedListByNearestLocation).build();
            } else {
                return GetAtmLocationRs.builder().build();
            }
        } catch (Exception e){
            log.error("Error getAtmByLocation", e);
            throw e;
        }
    }


}
