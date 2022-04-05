package com.mygroup.coursework.gui.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mygroup.coursework.gui.InputField;
import com.mygroup.coursework.gui.net.Request;

public class ClientDto {

  @JsonProperty("id")
  @InputField(name = "Id", usedIn = {Request.FIND_BY_ID, Request.DELETE_BY_ID})
  private long id;

  @JsonProperty("first_name")
  @InputField(name = "First name",
      usedIn = {Request.FIND_BY_FULL_NAME, Request.DELETE_BY_FULL_NAME, Request.SAVE})
  private String firstName;

  @JsonProperty("last_name")
  @InputField(name = "Last name",
      usedIn = {Request.FIND_BY_FULL_NAME, Request.DELETE_BY_FULL_NAME, Request.SAVE})
  private String lastName;

  @JsonProperty("partner_name")
  @InputField(name = "Partner name", usedIn = {Request.SAVE})
  private String partnerName;

  @JsonProperty("passport_seria")
  @InputField(name = "Passport seria", usedIn = {Request.SAVE})
  private String passportSeria;

  @JsonProperty("passport_num")
  @InputField(name = "Passport num", usedIn = {Request.SAVE})
  private String passportNum;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getPartnerName() {
    return partnerName;
  }

  public void setPartnerName(String partnerName) {
    this.partnerName = partnerName;
  }

  public String getPassportSeria() {
    return passportSeria;
  }

  public void setPassportSeria(String passportSeria) {
    this.passportSeria = passportSeria;
  }

  public String getPassportNum() {
    return passportNum;
  }

  public void setPassportNum(String passportNum) {
    this.passportNum = passportNum;
  }
}
