package com.asset.reservation.asset.service;

import com.asset.reservation.asset.entity.Reservation;
import com.asset.reservation.asset.payload.ReservationPayload;

import java.util.List;

public interface ReservationService {
  Reservation saveReservation(ReservationPayload reservationPayload);

  Reservation cancelReservation(String reservationId);

  List<Reservation> getReservations();
}
