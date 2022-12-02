package com.asset.reservation.asset.security;

import com.asset.reservation.asset.util.constants.ApplicationRoles;
import com.asset.reservation.asset.util.constants.ShellCommand;
import com.asset.reservation.asset.util.enums.AvailabilityLabels;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellMethodAvailability;

public abstract class AuthAvailability {

  @ShellMethodAvailability({
    ShellCommand.CATALOG,
    ShellCommand.BOOK,
    ShellCommand.CANCEL,
    ShellCommand.RESERVATION
  })
  public Availability isUserConnected() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (!(authentication instanceof UsernamePasswordAuthenticationToken)) {
      return Availability.unavailable(AvailabilityLabels.LOGGED_IN.value);
    }
    return Availability.available();
  }

  @ShellMethodAvailability({ShellCommand.CREATE_USER})
  public Availability isUserAdmin() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (!(authentication instanceof UsernamePasswordAuthenticationToken)) {
      return Availability.unavailable(AvailabilityLabels.LOGGED_IN.value);
    }
    if (!authentication
        .getAuthorities()
        .contains(new SimpleGrantedAuthority(ApplicationRoles.ADMIN))) {
      return Availability.unavailable(AvailabilityLabels.REQUIRED_ADMIN_PRIVILEGES.value);
    }
    return Availability.available();
  }
}
