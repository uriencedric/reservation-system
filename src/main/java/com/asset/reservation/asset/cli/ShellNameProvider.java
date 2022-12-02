package com.asset.reservation.asset.cli;

import com.asset.reservation.asset.service.UserService;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

@Component
public class ShellNameProvider implements PromptProvider {

  private final UserService userService;

  public ShellNameProvider(UserService userService) {
    this.userService = userService;
  }

  @Override
  public AttributedString getPrompt() {

    String shell;

    if (!userService.isConnected()) {
      shell = "<asset-reservation-system: anonymous>";
    } else {
      shell =
          String.format(
              "<asset-reservation-system: %s>", userService.getAuthenticatedUser().getFirstName());
    }
    return new AttributedString(shell, AttributedStyle.DEFAULT.foreground(AttributedStyle.BLUE));
  }
}
