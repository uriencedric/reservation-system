package com.asset.reservation.asset.service.impl;

import com.asset.reservation.asset.entity.Asset;
import com.asset.reservation.asset.entity.Reservation;
import com.asset.reservation.asset.exception.BusinessException;
import com.asset.reservation.asset.payload.ReservationPayload;
import com.asset.reservation.asset.repository.ReservationRepository;
import com.asset.reservation.asset.service.AssetService;
import com.asset.reservation.asset.service.ReservationService;
import com.asset.reservation.asset.service.UserService;
import com.asset.reservation.asset.util.Predicates;
import com.asset.reservation.asset.util.enums.BusinessExceptionLabels;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {

  private final ReservationRepository reservationRepository;
  private final AssetService assetService;
  private final UserService userService;

  @Autowired
  public ReservationServiceImpl(
      ReservationRepository reservationRepository,
      AssetService assetService,
      UserService userService) {
    this.reservationRepository = reservationRepository;
    this.assetService = assetService;
    this.userService = userService;
  }

  @Override
  public Reservation saveReservation(ReservationPayload reservationPayload) {
    Asset asset = assetService.getAsset(reservationPayload.getAsset_Id());
    throwIfInvalidAction(asset);
    asset.setAssetQuantity(asset.getAssetQuantity() - 1);
    assetService.saveAsset(asset);

    Reservation reservation =
        Reservation.builder()
            ._id(reservationPayload.getReservation_Id())
            .reservationStartDate(reservationPayload.getReservationStartDate())
            .reservationEndDate(reservationPayload.getReservationEndDate())
            .reservationSubmittedAt(new Date())
            .active(true)
            .asset(asset)
            .user(reservationPayload.getUser())
            .build();
    return reservationRepository.save(reservation);
  }

  @Override
  public Reservation cancelReservation(String reservationId) {

    Reservation reservation = reservationRepository.getReservationBy_idAndUserUsername(reservationId, userService.getAuthenticatedUser().getUsername());
    
    if (reservation == null) {
      throw new BusinessException(BusinessExceptionLabels.RESERVATION_NOT_FOUND);
    }

    if (!reservation.isActive()) {
      throw new BusinessException(BusinessExceptionLabels.RESERVATION_ALREADY_CANCELLED);
    }

    reservation.setActive(false);
    Asset asset = reservation.getAsset();
    asset.setAssetQuantity(reservation.getAsset().getAssetQuantity() + 1);
    assetService.saveAsset(asset);
    reservation.setAsset(asset);
    return reservationRepository.save(reservation);
  }

  private void throwIfInvalidAction(Asset asset) {

    if (asset.getAssetQuantity() < 1) {
      throw new BusinessException(BusinessExceptionLabels.ASSET_UNAVAILABLE);
    }
    Optional<Reservation> reservation =
        Optional.ofNullable(reservationRepository.getReservationsByAsset__id(asset.get_id()));

    reservation.ifPresent(
        res -> {
          String username = res.getUser().getUsername();
          if (username.equals(userService.getAuthenticatedUser().getUsername())) {
            throw new BusinessException(BusinessExceptionLabels.RESERVATION_FOUND);
          }
          if (!username.equals(userService.getAuthenticatedUser().getUsername())) {
            throw new BusinessException(
                BusinessExceptionLabels.RESERVATION_NOT_RELATED_TO_USER_FOUND);
          }
        });
  }

  public List<Reservation> getReservations() {
    List<Reservation> reservations =
        reservationRepository.getReservationsByUserUsername(
            userService.getAuthenticatedUser().getUsername());
    if (Predicates.reservationListNotEmpty().test(reservations)) {
      throw new BusinessException(BusinessExceptionLabels.CATALOG_EMPTY);
    }
    return reservations;
  }
}
