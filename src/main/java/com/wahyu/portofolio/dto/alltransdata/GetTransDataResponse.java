package com.wahyu.portofolio.dto.alltransdata;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetTransDataResponse {

    private Integer totalPages;
    private Long totalElements;
    private Integer numberOfElements;
    private Integer pageNumber;
    private List<GetTransDataDto> getTransData;
}
