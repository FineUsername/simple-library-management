package com.mygroup.coursework.gui.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.mygroup.coursework.gui.InputField;
import com.mygroup.coursework.gui.net.Request;

public class BookDto {

  @InputField(name = "Id", usedIn = {Request.FIND_BY_ID, Request.DELETE_BY_ID})
  @JsonProperty("id")
  private long id;

  @JsonProperty("name")
  @InputField(name = "Name", usedIn = {Request.FIND_BY_NAME, Request.DELETE_BY_NAME, Request.SAVE})
  private String name;

  @JsonProperty("count")
  @InputField(name = "Count", usedIn = {Request.SAVE})
  private int cnt;

  @JsonProperty("type_id")
  @InputField(name = "Type id", usedIn = {Request.SAVE})
  private long typeId;

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

  @JsonGetter("type_id")
  public long getTypeId() {
    return typeId;
  }

  @JsonSetter("type_id")
  public void setTypeId(long typeId) {
    this.typeId = typeId;
  }
}
