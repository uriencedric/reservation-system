package com.asset.reservation.asset.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Builder
@Getter
@Setter
public class ReservationDto {
  private String _id;
  private boolean active;
  private Date reservationStartDate;
  private Date reservationEndDate;
  private Date reservationSubmittedAt;
  private String employeeFirstName;
  private String employeeLastName;
  private String assetName;
}
