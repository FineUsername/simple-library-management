package com.mygroup.coursework.security;

public enum UserRole {
  USER, ADMIN;

  public String getPrefixedName() {
    return "ROLE_" + name();
  }
}
