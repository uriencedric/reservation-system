package com.asset.reservation.asset.cli;

import com.asset.reservation.asset.util.ConsoleUtils;
import com.asset.reservation.asset.cli.lib.Reader;
import com.asset.reservation.asset.entity.User;
import com.asset.reservation.asset.security.AuthAvailability;
import com.asset.reservation.asset.service.UserService;
import com.asset.reservation.asset.util.constants.ShellMethodLabels;
import com.asset.reservation.asset.util.enums.ReadInputLabels;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.util.StringUtils;

@ShellComponent
public class AuthCommand extends AuthAvailability {

  private final Reader reader;
  private final AuthenticationManager authenticationManager;
  private final UserService userService;

  @Lazy
  @Autowired
  public AuthCommand(
      Reader reader, AuthenticationManager authenticationManager, UserService userService) {
    this.reader = reader;
    this.authenticationManager = authenticationManager;
    this.userService = userService;
  }

  @ShellMethod(ShellMethodLabels.LOGIN)
  public void login() {
    String username;
    String password;
    boolean flag = true;
    do {
      username = reader.readInput(ReadInputLabels.ENTER_USERNAME);
      password = reader.readPassword(ReadInputLabels.ENTER_PASSWORD);
      if (StringUtils.hasText(username) || StringUtils.hasText(username)) {
        flag = false;
      } else {
        ConsoleUtils.printMessage(ReadInputLabels.WRONG_AUTH_INPUT.value);
      }
    } while (flag);
    try {
      Authentication authenticationToken =
          new UsernamePasswordAuthenticationToken(username, password);
      Authentication result = authenticationManager.authenticate(authenticationToken);
      SecurityContextHolder.getContext().setAuthentication(result);
      User user = userService.findByUsername(username);
      userService.connect();
      userService.setAuthenticatedUser(user);
      ConsoleUtils.printMessage(
          ReadInputLabels.AUTH_SUCCESS.value
              + " Welcome -> "
              + user.getFirstName()
              + " "
              + user.getLastName());
    } catch (AuthenticationException e) {
      ConsoleUtils.printMessage(ReadInputLabels.AUTH_FAILED.value);
    }
  }

  @ShellMethod(ShellMethodLabels.LOGOUT)
  public void logout() {
    SecurityContextHolder.getContext().setAuthentication(null);
    userService.setAuthenticatedUser(null);
    userService.disconnect();
  }

  @ShellMethod(ShellMethodLabels.CLOSE)
  public void close() {
    System.exit(0);
  }

  @ShellMethod(ShellMethodLabels.CREATE_USER)
  public void createUser() {
    ConsoleUtils.printMessage("Feature available soon !");
  }
}
