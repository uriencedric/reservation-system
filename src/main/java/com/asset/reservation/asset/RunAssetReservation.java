package com.asset.reservation.asset;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.StringUtils;

@SpringBootApplication
public class RunAssetReservation {

  private static final String[] DISABLED_BUILTIN_CMD = {
    "--spring.shell.command.script.enabled=false",
    "--spring.shell.command.exit.enabled=false",
    "--spring.shell.command.quit.enabled=false",
    "--spring.shell.command.clear.enabled=false",
    "--spring.shell.command.stacktrace.enabled=false"
  };

  public static void main(String[] args) {
    String[] userArgs = StringUtils.concatenateStringArrays(args, DISABLED_BUILTIN_CMD);
    SpringApplication.run(RunAssetReservation.class, userArgs);
  }
}
