package com.mygroup.coursework.gui.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mygroup.coursework.gui.InputField;
import com.mygroup.coursework.gui.net.Request;

public class BookTypeDto {

  @JsonProperty("id")
  @InputField(name = "Id", usedIn = {Request.FIND_BY_ID, Request.DELETE_BY_ID})
  private long id;

  @JsonProperty("name")
  @InputField(name = "Name", usedIn = {Request.FIND_BY_NAME, Request.DELETE_BY_NAME, Request.SAVE})
  private String name;

  @JsonProperty("count")
  @InputField(name = "Count", usedIn = {Request.SAVE})
  private int cnt;

  @JsonProperty("fine")
  @InputField(name = "Fine", usedIn = {Request.SAVE})
  private long fine;

  @InputField(name = "Day count", usedIn = {Request.SAVE})
  @JsonProperty("day_count")
  private int dayCount;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getCnt() {
    return cnt;
  }

  public void setCnt(int cnt) {
    this.cnt = cnt;
  }

  public long getFine() {
    return fine;
  }

  public void setFine(long fine) {
    this.fine = fine;
  }

  public int getDayCount() {
    return dayCount;
  }

  public void setDayCount(int dayCount) {
    this.dayCount = dayCount;
  }
}
