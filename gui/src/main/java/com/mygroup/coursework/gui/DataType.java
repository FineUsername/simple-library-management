package com.mygroup.coursework.gui;

import static com.mygroup.coursework.gui.net.Request.*;
import com.mygroup.coursework.gui.dto.BookDto;
import com.mygroup.coursework.gui.dto.BookTypeDto;
import com.mygroup.coursework.gui.dto.ClientDto;
import com.mygroup.coursework.gui.dto.JournalEntryDto;
import com.mygroup.coursework.gui.dto.UserDto;
import java.util.List;
import com.mygroup.coursework.gui.net.Request;

public enum DataType {
  BOOK("/books", BookDto.class,
      List.of(FIND_ALL, FIND_BY_ID, FIND_BY_NAME, SAVE, DELETE_ALL, DELETE_BY_ID,
          DELETE_BY_NAME)), BOOK_TYPE(
              "/bookTypes", BookTypeDto.class,
              List.of(FIND_ALL, FIND_BY_ID, FIND_BY_NAME, SAVE, DELETE_ALL, DELETE_BY_ID,
                  DELETE_BY_NAME)), CLIENT(
                      "/clients", ClientDto.class,
                      List.of(FIND_ALL, FIND_BY_ID, FIND_BY_FULL_NAME, SAVE, DELETE_ALL,
                          DELETE_BY_ID, DELETE_BY_FULL_NAME)), JOURNAL_ENTRY("/journal",
                              JournalEntryDto.class,
                              List.of(FIND_ALL, FIND_BY_ID, SAVE, DELETE_ALL, DELETE_BY_ID)), USER(
                                  "/users", UserDto.class, List.of(SIGN_IN, SIGN_UP));

  private final String mapping;
  private final List<Request> supportedRequests;
  private final Class<?> type;

  private DataType(String mapping, Class<?> type, List<Request> supportedRequests) {
    this.mapping = mapping;
    this.type = type;
    this.supportedRequests = supportedRequests;
  }

  public String getMapping() {
    return mapping;
  }

  public boolean isSupported(Request request) {
    return supportedRequests.contains(request);
  }

  public List<Request> getSupportedRequests() {
    return supportedRequests;
  }

  public Class<?> getType() {
    return type;
  }
}
