package com.asset.reservation.asset.payload;

import com.asset.reservation.asset.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Builder
@Getter
@Setter
public class ReservationPayload {
  private Date reservationStartDate;
  private Date reservationEndDate;
  private Date reservationSubmittedAt;
  private User user;
  private String reservation_Id;
  private String asset_Id;
}
