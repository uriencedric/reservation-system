package com.asset.reservation.asset.service.impl;

import com.asset.reservation.asset.entity.Asset;
import com.asset.reservation.asset.entity.Reservation;
import com.asset.reservation.asset.entity.User;
import com.asset.reservation.asset.exception.BusinessException;
import com.asset.reservation.asset.payload.ReservationPayload;
import com.asset.reservation.asset.repository.ReservationRepository;
import com.asset.reservation.asset.service.AssetService;
import com.asset.reservation.asset.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservationServiceImplTest {

  @Mock private ReservationRepository reservationRepository;
  @Mock private AssetService assetService;
  @Mock private UserService userService;
  @InjectMocks private ReservationServiceImpl reservationService;

  @BeforeEach
  void setUp() {}

  @Test
  void when_save_reservation_with_zero_asset_qty_should_throw_ex() {
    Asset asset = new Asset();
    asset.setId(1L);
    asset.setAssetQuantity(0L);
    asset.set_id("assetId");

    Reservation reservation = Reservation.builder().id(1L).asset(asset).build();

    when(assetService.getAsset(asset.get_id())).thenReturn(asset);

    assertThrows(
        Exception.class,
        () ->
            reservationService.saveReservation(
                ReservationPayload.builder().asset_Id(reservation.getAsset().get_id()).build()));
  }

  @Test
  void when_save_reservation_with_good_asset_id_should_pass() {

    Asset asset = new Asset();
    asset.setId(1L);
    asset.setAssetQuantity(1L);
    asset.set_id("assetId");

    Reservation reservation = Reservation.builder().id(1L).asset(asset).build();

    when(assetService.getAsset(asset.get_id())).thenReturn(asset);

    assertDoesNotThrow(
        () ->
            reservationService.saveReservation(
                ReservationPayload.builder().asset_Id(reservation.getAsset().get_id()).build()));
  }

  @Test
  void when_save_reservation_with_existing_reservation_id_should_throw_ex() {

    Asset asset = new Asset();
    asset.setId(1L);
    asset.setAssetQuantity(1L);
    asset.set_id("assetId");

    User user = new User();
    user.setUsername("foo.bar");

    Reservation reservation = Reservation.builder().id(1L).asset(asset).user(user).build();

    when(userService.getAuthenticatedUser()).thenReturn(user);
    when(assetService.getAsset(asset.get_id())).thenReturn(asset);
    when(reservationRepository.getReservationsByAsset__id(asset.get_id())).thenReturn(reservation);

    assertThrows(
        BusinessException.class,
        () ->
            reservationService.saveReservation(
                ReservationPayload.builder().asset_Id(reservation.getAsset().get_id()).build()));
  }

  @Test
  void when_save_reservation_with_unrelated_reservation_id_should_throw_ex() {
    Asset asset = new Asset();
    asset.setId(1L);
    asset.setAssetQuantity(1L);
    asset.set_id("assetId");

    User user = new User();
    user.setUsername("foo.bar");
    User auth = new User();
    auth.setUsername("bar.foo");

    Reservation reservation = Reservation.builder().id(1L).asset(asset).user(user).build();
    userService.setAuthenticatedUser(auth);

    when(userService.getAuthenticatedUser()).thenReturn(auth);
    when(assetService.getAsset(asset.get_id())).thenReturn(asset);
    when(reservationRepository.getReservationsByAsset__id(asset.get_id())).thenReturn(reservation);

    assertThrows(
        BusinessException.class,
        () ->
            reservationService.saveReservation(
                ReservationPayload.builder().asset_Id(reservation.getAsset().get_id()).build()));
  }

  @Test
  void when_cancel_reservation_with_wrong_id_should_throw_ex() {
    User auth = new User();
    auth.setUsername("bar.foo");
    userService.setAuthenticatedUser(auth);

    when(userService.getAuthenticatedUser()).thenReturn(auth);
    when(reservationRepository.getReservationBy_idAndUserUsername("non_good", auth.getUsername())).thenReturn(null);

    assertThrows(BusinessException.class, () -> reservationService.cancelReservation("non_good"));
  }

  @Test
  void when_cancel_reservation_with_good_id_should_pass() {
    Asset asset = new Asset();
    asset.setId(1L);
    asset.setAssetQuantity(1L);
    asset.set_id("assetId");

    User auth = new User();
    auth.setUsername("bar.foo");
    userService.setAuthenticatedUser(auth);

    Reservation reservation = Reservation.builder()._id("good").asset(asset).active(true).build();

    when(userService.getAuthenticatedUser()).thenReturn(auth);
    when(reservationRepository.getReservationBy_idAndUserUsername("good", auth.getUsername())).thenReturn(reservation);

    assertDoesNotThrow(() -> reservationService.cancelReservation("good"));
  }

  @Test
  void when_cancel_reservation_with_good_id_should_raise_qty_by_1() {
    Asset asset = new Asset();
    asset.setId(1L);
    asset.setAssetQuantity(1L);
    asset.set_id("assetId");

    User auth = new User();
    auth.setUsername("bar.foo");
    userService.setAuthenticatedUser(auth);

    when(userService.getAuthenticatedUser()).thenReturn(auth);
    Reservation reservation = Reservation.builder()._id("good").asset(asset).active(true).build();
    when(reservationRepository.getReservationBy_idAndUserUsername("good", auth.getUsername())).thenReturn(reservation);
    when(reservationRepository.save(reservation)).thenReturn(reservation);

    Reservation cancelReservation = reservationService.cancelReservation("good");

    assertEquals(cancelReservation.getAsset().getAssetQuantity(), 2);
  }

  @Test
  void when_cancel_reservation_with_good_id_should_set_is_active_to_false() {
    Asset asset = new Asset();
    asset.setId(1L);
    asset.setAssetQuantity(1L);
    asset.set_id("assetId");

    Reservation reservation = Reservation.builder()._id("good").asset(asset).active(true).build();

    User auth = new User();
    auth.setUsername("bar.foo");
    userService.setAuthenticatedUser(auth);

    when(userService.getAuthenticatedUser()).thenReturn(auth);
    when(reservationRepository.getReservationBy_idAndUserUsername("good", auth.getUsername())).thenReturn(reservation);
    when(reservationRepository.save(reservation)).thenReturn(reservation);

    Reservation cancelReservation = reservationService.cancelReservation("good");

    assertFalse(cancelReservation.isActive());
  }

  @Test
  void when_reservations_are_empty_should_throw_an_exception() {
    String username = "notRelatedUser";
    List<Reservation> reservationList = new ArrayList<>();
    User user = new User();
    user.setUsername(username);
    userService.setAuthenticatedUser(user);

    when(userService.getAuthenticatedUser()).thenReturn(user);
    when(reservationRepository.getReservationsByUserUsername(username))
        .thenReturn(reservationList);

    assertThrows(BusinessException.class, () -> reservationService.getReservations());
  }

  @Test
  void when_reservations_are_found_should_return_a_list_of_reservations() {
    String username = "related";
    List<Reservation> reservationList = new ArrayList<>();
    reservationList.add(new Reservation());

    User user = new User();
    user.setUsername(username);
    userService.setAuthenticatedUser(user);

    when(userService.getAuthenticatedUser()).thenReturn(user);
    when(reservationRepository.getReservationsByUserUsername(username))
            .thenReturn(reservationList);

    assertEquals(reservationService.getReservations().size(), reservationList.size());
  }
}
