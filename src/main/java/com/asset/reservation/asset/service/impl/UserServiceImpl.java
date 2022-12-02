package com.asset.reservation.asset.service.impl;

import com.asset.reservation.asset.entity.User;
import com.asset.reservation.asset.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class UserServiceImpl implements com.asset.reservation.asset.service.UserService {

  final UserRepository userRepository;
  private final AtomicBoolean isConnected = new AtomicBoolean();
  private final AtomicReference<User> userAtomicReference = new AtomicReference<>();

  @Autowired
  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public User findByUsername(String username) {
    return userRepository.getByUsername(username);
  }

  public User getAuthenticatedUser() {
    return userAtomicReference.get();
  }

  @Override
  public void setAuthenticatedUser(User user) {
    userAtomicReference.set(user);
  }

  public boolean isConnected() {
    return this.isConnected.get();
  }

  public void connect() {
    this.isConnected.set(true);
  }

  public void disconnect() {
    this.isConnected.set(false);
  }
}
