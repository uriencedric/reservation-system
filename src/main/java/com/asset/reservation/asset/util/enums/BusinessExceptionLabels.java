package com.asset.reservation.asset.util.enums;

public enum BusinessExceptionLabels {
  ASSET_NOT_FOUND("Asset not Found"),
  RESERVATION_NOT_FOUND("Reservation not Found"),
  RESERVATION_FOUND("You have already a reservation for this asset."),
  RESERVATION_NOT_RELATED_TO_USER_FOUND("You cannot perform this action."),
  ASSET_UNAVAILABLE("Asset unavailable"),
  INVALID_RESERVATION_ITEM_REQUEST("Cannot save more than one item"),
  INVALID_DATE_INTERVAL_SUPPLIED("Invalid date interval supplied."),
  RESERVATION_ALREADY_CANCELLED("The Reservation is already cancelled"),
  CATALOG_RESULTS_UNAVAILABLE("Cannot retrieve results"),
  RESERVATION_FAILED(
      "Error while saving asset. Cannot continue reservation. Please resubmit the form."),
  CATALOG_EMPTY("Nothing to display"),
  WRONG_USER_INT_PARAMETER_PROVIDED("Input must be an integer."),
  WRONG_USER_INPUT_PROVIDED("Wrong user input provided"),
  ;

  private final String value;

  BusinessExceptionLabels(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
