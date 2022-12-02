package com.asset.reservation.asset.repository;

import com.asset.reservation.asset.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
  Reservation getReservationBy_idAndUserUsername(String _id, String username);

  Reservation getReservationsByAsset__id(String _id);

  List<Reservation> getReservationsByUserUsername(String username);
}
