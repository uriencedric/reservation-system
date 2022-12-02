package com.asset.reservation.asset.security;

import com.asset.reservation.asset.entity.User;
import com.asset.reservation.asset.service.UserService;
import com.asset.reservation.asset.util.constants.ApplicationRoles;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class ApplicationUserDetailsService implements UserDetailsService {

  private final UserService userService;

  public ApplicationUserDetailsService(UserService userService) {
    this.userService = userService;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userService.findByUsername(username);

    if (user == null) {
      throw new UsernameNotFoundException("Cannot retrieve user information.");
    }

    org.springframework.security.core.userdetails.User.UserBuilder userBuilder =
        org.springframework.security.core.userdetails.User.withUsername(username);

    userBuilder.password(new BCryptPasswordEncoder().encode(user.getPassword()));

    if (user.getSeniority().contains("MANAGER")) {
      userBuilder.roles(ApplicationRoles.MANAGER_ROLE_ARRAY);
    } else {
      userBuilder.roles(ApplicationRoles.USER_ROLE_ARRAY);
    }
    return userBuilder.build();
  }
}
