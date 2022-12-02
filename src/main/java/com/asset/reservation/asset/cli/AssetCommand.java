package com.asset.reservation.asset.cli;

import com.asset.reservation.asset.util.ConsoleUtils;
import com.asset.reservation.asset.cli.lib.Reader;
import com.asset.reservation.asset.dto.AssetDto;
import com.asset.reservation.asset.dto.ReservationDto;
import com.asset.reservation.asset.entity.Asset;
import com.asset.reservation.asset.entity.Reservation;
import com.asset.reservation.asset.exception.BusinessException;
import com.asset.reservation.asset.payload.ReservationPayload;
import com.asset.reservation.asset.security.AuthAvailability;
import com.asset.reservation.asset.service.AssetService;
import com.asset.reservation.asset.service.ReservationService;
import com.asset.reservation.asset.service.UserService;
import com.asset.reservation.asset.service.impl.AssetServiceImpl;
import com.asset.reservation.asset.util.DateUtils;
import com.asset.reservation.asset.util.Predicates;
import com.asset.reservation.asset.util.constants.ShellMethodLabels;
import com.asset.reservation.asset.util.enums.BusinessExceptionLabels;
import com.asset.reservation.asset.util.enums.ReadInputLabels;
import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.table.BeanListTableModel;
import org.springframework.shell.table.TableModel;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ShellComponent
public class AssetCommand extends AuthAvailability {

  private static final int RESERVATION_ID_STRING_LENGTH = 15;
  private static final int CATALOG_TABLE_WIDTH = 80;
  private static final int RESERVATION_TABLE_WIDTH = 150;
  private static final Logger log = LoggerFactory.getLogger(AssetCommand.class);
  private final UserService userService;

  private final ReservationService reservationService;
  private final AssetService assetService;
  private final ModelMapper modelMapper;
  private final Reader reader;

  @Lazy
  @Autowired
  public AssetCommand(
      UserService userService,
      ReservationService reservationService,
      AssetService assetService,
      ModelMapper modelMapper,
      Reader lineReader) {
    this.userService = userService;
    this.reservationService = reservationService;
    this.assetService = assetService;
    this.modelMapper = modelMapper;
    this.reader = lineReader;
  }

  @ShellMethod(ShellMethodLabels.DISPLAY_CATALOG)
  public void catalog() {
    String assetTypeInput =
        reader.readInput(
            ReadInputLabels.DISPLAY_ASSET.value, "laptop", Predicates.inputIsAuthorizedAssetType());

    int totalPages = assetService.getPaginatedAssetsByTypeNameResults(assetTypeInput);

    String pageInput =
        reader.readInput(
            String.format(ReadInputLabels.DISPLAY_PAGE_FORMATTING.value, totalPages),
            "1",
            Predicates.creatable());

    int currentPage =
        pageInput == null ? AssetServiceImpl.DEFAULT_PAGE_NUMBER : Integer.parseInt(pageInput);

    if (currentPage < 1) {
      throw new BusinessException(BusinessExceptionLabels.CATALOG_RESULTS_UNAVAILABLE);
    }

    List<Asset> content =
        assetService
            .getPaginatedAssetsByTypeName(
                assetTypeInput, currentPage - 1, AssetServiceImpl.DEFAULT_PAGE_SIZE, List::isEmpty)
            .getContent();

    List<AssetDto> paginatedAssets =
        modelMapper.map(content, new TypeToken<List<AssetDto>>() {}.getType());

    TableModel beanListTableModel =
        new BeanListTableModel(paginatedAssets, ConsoleUtils.getHeaderAssetMap());
    ConsoleUtils.generateTable(beanListTableModel, totalPages, currentPage, CATALOG_TABLE_WIDTH);
  }

  @ShellMethod(ShellMethodLabels.RESERVE_ASSET)
  public void book() {
    ConsoleUtils.printMessage(ReadInputLabels.FILL_INTERACTIVE_FORM_RESERVATION_TITLE.value);
    try {
      String asset_Id =
          reader.readInput(
              ReadInputLabels.FILL_INTERACTIVE_FORM_ASSET_ID.value, Predicates.inputIsNotBlank());

      LocalDateTime startDate =
          reader.readDateInput(ReadInputLabels.FILL_INTERACTIVE_FORM_START_DATE);
      LocalDateTime endDate = reader.readDateInput(ReadInputLabels.FILL_INTERACTIVE_FORM_END_DATE);

      if (!DateUtils.isDateIntervalValid(startDate, endDate)) {
        throw new BusinessException(BusinessExceptionLabels.INVALID_DATE_INTERVAL_SUPPLIED);
      }

      ReservationPayload reservationPayload =
          ReservationPayload.builder()
              .reservation_Id(RandomStringUtils.randomAlphanumeric(RESERVATION_ID_STRING_LENGTH))
              .reservationStartDate(DateUtils.localDateTimeToDate(startDate))
              .reservationEndDate(DateUtils.localDateTimeToDate(endDate))
              .reservationSubmittedAt(new Date())
              .asset_Id(asset_Id)
              .user(userService.getAuthenticatedUser())
              .build();

      reservationService.saveReservation(reservationPayload);
      ConsoleUtils.printMessage(ReadInputLabels.FILL_INTERACTIVE_FORM_RESERVATION_RESULT_MSG.value);

    } catch (NumberFormatException | ParseException e) {
      log.error(e.getMessage());
      throw new BusinessException(BusinessExceptionLabels.RESERVATION_FAILED);
    }
  }

  @ShellMethod(ShellMethodLabels.DISPLAY_USER_RESERVATION)
  public void reservation() {
    List<Reservation> reservations = reservationService.getReservations();
    List<ReservationDto> reservationDtos = new ArrayList<>();
    reservations.forEach(
        reservation ->
            reservationDtos.add(
                ReservationDto.builder()
                    .active(reservation.isActive())
                    ._id(reservation.get_id())
                    .employeeFirstName(reservation.getUser().getFirstName())
                    .employeeLastName(reservation.getUser().getLastName())
                    .reservationStartDate(reservation.getReservationStartDate())
                    .reservationEndDate(reservation.getReservationEndDate())
                    .reservationSubmittedAt(reservation.getReservationSubmittedAt())
                    .assetName(reservation.getAsset().getAssetName())
                    .build()));

    TableModel beanListTableModel =
        new BeanListTableModel(reservationDtos, ConsoleUtils.getHeaderReservationMap());
    ConsoleUtils.generateTable(beanListTableModel, RESERVATION_TABLE_WIDTH);
  }

  @ShellMethod(ShellMethodLabels.CANCEL_RESERVATION)
  public void cancel() {
    ConsoleUtils.printMessage(ReadInputLabels.FILL_INTERACTIVE_FORM_CANCEL_TITLE.value);
    String reservationId =
        reader.readInput(ReadInputLabels.FILL_INTERACTIVE_FORM_RESERVATION_ID.value);
    reservationService.cancelReservation(reservationId);
    ConsoleUtils.printMessage(ReadInputLabels.FILL_INTERACTIVE_FORM_CANCEL_RESULT_MSG.value);
  }
}
