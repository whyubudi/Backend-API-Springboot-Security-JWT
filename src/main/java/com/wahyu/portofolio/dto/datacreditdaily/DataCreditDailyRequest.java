package com.wahyu.portofolio.dto.datacreditdaily;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class DataCreditDailyRequest {

    @ApiModelProperty(value = "ROWSTART", example = "1")
    private String rowstart;
    @ApiModelProperty(value = "PAGE", example = "50")
    private String page;
    private Timestamp Date;
}
