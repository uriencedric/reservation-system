package com.asset.reservation.asset.cli.lib;

import com.asset.reservation.asset.exception.BusinessException;
import com.asset.reservation.asset.util.DateUtils;
import com.asset.reservation.asset.util.Predicates;
import com.asset.reservation.asset.util.enums.BusinessExceptionLabels;
import com.asset.reservation.asset.util.enums.ReadInputLabels;
import org.apache.commons.lang3.StringUtils;
import org.jline.reader.LineReader;

import java.io.Console;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.function.Predicate;

public class Reader {

  public static final Character CLI_DEFAULT_MASK = '*';

  private final Character cliMask;

  private final LineReader reader;

  public Reader(LineReader reader, Character mask) {
    this.reader = reader;
    this.cliMask = mask != null ? mask : CLI_DEFAULT_MASK;
  }

  public String readPassword(ReadInputLabels input) {
    Console console = System.console();
    if (console != null) {
      return String.valueOf(console.readPassword(input.value + " : "));
    }
    return readInput(input.value);
  }

  public String readInput(String input) {
    return readInput(input, null, true);
  }

  public String readInput(ReadInputLabels input) {
    return readInput(input.value, null, true);
  }

  public String readInput(String input, String defaultValue, Predicate<String> predicate) {
    String userInput = readInput(input, defaultValue, true);
    if (predicate.negate().test(userInput)) {
      throw new BusinessException(BusinessExceptionLabels.WRONG_USER_INPUT_PROVIDED);
    }
    return userInput;
  }

  public String readInput(String input, Predicate<String> predicate) {
    String userInput = readInput(input, null, true);
    if (predicate.negate().test(userInput)) {
      throw new BusinessException(BusinessExceptionLabels.WRONG_USER_INPUT_PROVIDED);
    }
    return userInput;
  }

  public String readInput(String input, String defaultValue, boolean echo) {
    String userData;

    if (echo) {
      userData = reader.readLine(input + ": ");
    } else {
      userData = reader.readLine(input + ": ", cliMask);
    }
    if (StringUtils.isEmpty(userData)) {
      return defaultValue;
    }
    return userData;
  }

  public LocalDateTime readDateInput(ReadInputLabels fillInteractiveFormEndDate)
      throws ParseException {
    String input = readInput(fillInteractiveFormEndDate.value, Predicates.dateIsParsable());
    return DateUtils.dateToLocalDateTime(DateUtils.parse(input));
  }
}
