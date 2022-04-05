package com.mygroup.coursework.gui.net;

public enum Request {
  FIND_ALL("/findAll", "GET"), FIND_BY_ID("/findById", "GET"), FIND_BY_NAME("/findByName",
      "GET"), FIND_BY_FULL_NAME("/findByFullName", "GET"), SAVE("/save", "POST"), DELETE_ALL(
          "/deleteAll", "DELETE"), DELETE_BY_ID("/deleteById", "DELETE"), DELETE_BY_NAME(
              "/deleteByName", "DELETE"), DELETE_BY_FULL_NAME("/deleteByFullName",
                  "DELETE"), SIGN_IN("/signIn", "POST"), SIGN_UP("/signUp", "POST");

  private final String mapping;
  private final String method;

  private Request(String mapping, String method) {
    this.mapping = mapping;
    this.method = method;
  }

  public String getMapping() {
    return mapping;
  }

  public String getMethod() {
    return method;
  }

}
