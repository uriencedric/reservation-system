package com.asset.reservation.asset.service;

import com.asset.reservation.asset.entity.User;

public interface UserService {
  User findByUsername(String username);

  User getAuthenticatedUser();

  void setAuthenticatedUser(User user);

  boolean isConnected();

  void connect();

  void disconnect();
}
