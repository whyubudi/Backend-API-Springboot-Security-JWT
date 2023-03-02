package com.wahyu.portofolio.dto.atmlocation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AtmLocationDto implements Serializable {
  private static final long serialVersionUID = 8678999651789443302L;

  private Long id;
  private float distance;
  private String atmMachine;
  private String atmName;
  private String atmAddress;
  private String longitude;
  private String latitude;
  private String googleMap;
  private Boolean status;
}
