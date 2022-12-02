package com.asset.reservation.asset.util.constants;

public class ApplicationRoles {
  public static final String[] USER_ROLE_ARRAY = {"USER", "SENIOR"};
  public static final String[] MANAGER_ROLE_ARRAY = {"USER", "MANAGER", "ADMIN"};
  // Spring seems to prefix roles...
  public static final String USER = "ROLE_USER";
  public static final String SENIOR = "ROLE_SENIOR";
  public static final String MANAGER = "ROLE_MANAGER";
  public static final String ADMIN = "ROLE_ADMIN";
}
