package com.wahyu.portofolio.dto.atmlocation;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAtmLocationRq implements Serializable {
    private static final long serialVersionUID = 5873607555993133021L;
    @ApiModelProperty(value = "-6.258091", example = "-6.258091")
    private double latitude;
    @ApiModelProperty(value = "106.777626", example = "106.777626")
    private double longitude;
}
