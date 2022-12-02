package com.asset.reservation.asset.util;

import com.asset.reservation.asset.entity.Asset;
import com.asset.reservation.asset.entity.Reservation;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class Predicates {

  private static final List<String> ALLOWED_CLI_ASSET_TYPE =
      Arrays.asList("laptop", "charger", "screen");

  private static final String DATE_FORMAT = "dd-MM-yy";

  public static Predicate<String> creatable() {
    return NumberUtils::isCreatable;
  }

  public static Predicate<String> inputIsNotBlank() {
    return s -> !StringUtils.isBlank(s);
  }

  public static Predicate<List<Reservation>> reservationListNotEmpty() {
    return List::isEmpty;
  }

  public static Predicate<String> inputIsAuthorizedAssetType() {
    return Predicates.ALLOWED_CLI_ASSET_TYPE::contains;
  }

  public static Predicate<String> dateIsParsable() {
    DateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
    simpleDateFormat.setLenient(false);

    return date -> {
      try {
        if (date == null) {
          return false;
        }
        simpleDateFormat.parse(date);
      } catch (ParseException e) {
        return false;
      }
      return true;
    };
  }
}
