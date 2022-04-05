package com.mygroup.coursework.gui.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mygroup.coursework.gui.InputField;
import com.mygroup.coursework.gui.net.Request;
import java.sql.Timestamp;

public class JournalEntryDto {

  @JsonProperty("id")
  @InputField(name = "Id", usedIn = {Request.FIND_BY_ID, Request.DELETE_BY_ID})
  private long id;

  @JsonProperty("book_id")
  @InputField(name = "Book id", usedIn = {Request.SAVE})
  private long bookId;

  @JsonProperty("client_id")
  @InputField(name = "Client id", usedIn = {Request.SAVE})
  private long clientId;

  @JsonProperty("date_beg")
  @InputField(name = "Date beg", usedIn = {Request.SAVE})
  private Timestamp dateBeg;

  @JsonProperty("date_end")
  @InputField(name = "Date end", usedIn = {Request.SAVE})
  private Timestamp dateEnd;

  @JsonProperty("date_ret")
  @InputField(name = "Date ret", usedIn = {Request.SAVE})
  private Timestamp dateRet;
}
