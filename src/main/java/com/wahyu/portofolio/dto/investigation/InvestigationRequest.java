package com.wahyu.portofolio.dto.investigation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvestigationRequest {

    private Long id;
    private String investigationDecision;
}
