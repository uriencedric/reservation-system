package com.asset.reservation.asset.util.enums;

import lombok.Getter;

@Getter
public enum DataTableReservationHeaders {
  RESERVATION_HEADER_ID("_id", "_Id"),
  RESERVATION_HEADER_EMPLOYEE_FIRST_NAME("employeeFirstName", "First Name"),
  RESERVATION_HEADER_EMPLOYEE_LAST_NAME("employeeLastName", "Last Name"),
  RESERVATION_HEADER_START_DATE("employeeLastName", "Last Name"),
  RESERVATION_HEADER_RESERVATION_START_DATE("reservationStartDate", "Reservation Start Date"),
  RESERVATION_HEADER_END_DATE("reservationEndDate", "Reservation End Date"),
  RESERVATION_HEADER_SUBMITTED_AT("reservationSubmittedAt", "Reservation Submit Date"),
  RESERVATION_HEADER_ASSET_NAME("assetName", "Asset Name");

  public String key;
  public String value;

  DataTableReservationHeaders(String key, String value) {
    this.value = value;
    this.key = key;
  }
}
