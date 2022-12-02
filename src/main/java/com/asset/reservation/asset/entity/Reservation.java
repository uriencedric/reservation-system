package com.asset.reservation.asset.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @NotNull(message = "_Id  cannot be null")
  private String _id;

  private boolean active;

  @NotNull(message = "Reservation Start Date cannot be null")
  private Date reservationStartDate;

  @NotNull(message = "Reservation End Date cannot be null")
  private Date reservationEndDate;

  @NotNull(message = "Reservation Submission Date cannot be null")
  private Date reservationSubmittedAt;

  @NotNull(message = "User cannot be null")
  @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
  private User user;

  @NotNull(message = "Asset cannot be null")
  @ManyToOne(targetEntity = Asset.class, fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
  private Asset asset;
}
