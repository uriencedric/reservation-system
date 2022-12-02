package com.asset.reservation.asset.repository;

import com.asset.reservation.asset.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  User getByUsername(String username);
}
