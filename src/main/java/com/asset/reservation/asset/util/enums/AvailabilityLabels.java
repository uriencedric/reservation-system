package com.asset.reservation.asset.util.enums;

import lombok.Getter;

@Getter
public enum AvailabilityLabels {
  LOGGED_IN("You must be logged in."),
  REQUIRED_ADMIN_PRIVILEGES("You have insufficient privileges to run this command.");

  public String value;

  AvailabilityLabels(String value) {
    this.value = value;
  }
}
