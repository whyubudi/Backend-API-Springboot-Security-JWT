package com.wahyu.portofolio.dto.investigation;

import com.bankmega.model.IssuerAlerts;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvestigationResponse {

    private IssuerAlerts issuerAlerts;
}
