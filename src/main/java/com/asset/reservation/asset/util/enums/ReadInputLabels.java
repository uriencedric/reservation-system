package com.asset.reservation.asset.util.enums;

import lombok.Getter;

@Getter
public enum ReadInputLabels {
  ENTER_USERNAME("Please enter your username"),
  ENTER_PASSWORD("Please enter your password"),
  WRONG_AUTH_INPUT("Wrong input received."),
  AUTH_SUCCESS("Authentication successful."),
  AUTH_FAILED("Authentication failed"),
  DISPLAY_ASSET("Which asset do you want to display ? [laptop]"),
  DISPLAY_PAGE_FORMATTING("Which page do you want to display ? [1 - %s]"),
  FILL_INTERACTIVE_FORM_RESERVATION_TITLE(
      "Please fill in the interactive form to reserve an asset"),
  FILL_INTERACTIVE_FORM_CANCEL_TITLE("Please fill in the interactive form to cancel an asset"),
  FILL_INTERACTIVE_FORM_ASSET_ID("Please enter asset _id"),
  FILL_INTERACTIVE_FORM_RESERVATION_ID("Please enter reservation _id"),
  FILL_INTERACTIVE_FORM_START_DATE("Please enter start date : dd-MM-yy"),
  FILL_INTERACTIVE_FORM_END_DATE("Please enter end date : dd-MM-yy"),
  FILL_INTERACTIVE_FORM_QTY("Please enter quantity : [max 1]"),
  FILL_INTERACTIVE_FORM_RESERVATION_RESULT_MSG(
      "Your reservation was successful. Please run 'reservation' to see your reservations."),
  FILL_INTERACTIVE_FORM_CANCEL_RESULT_MSG(
      "Your cancellation was successful. Please run 'reservation' to see your remaining reservations.");

  public String value;

  ReadInputLabels(String value) {
    this.value = value;
  }
}
