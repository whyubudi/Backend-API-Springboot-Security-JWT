package com.wahyu.portofolio.dto.atmlocation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAtmLocationRs implements Serializable {
    private static final long serialVersionUID = 4280912438548104321L;
    private List<AtmLocationDto> atmLocations;
}
