package com.mygroup.coursework.gui.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserDto {

  @JsonProperty("id")
  private long id;

  @JsonProperty("username")
  private String username;

  @JsonProperty("password")
  private String password;

  public UserDto(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
