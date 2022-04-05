package com.mygroup.coursework.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mygroup.coursework.EmptySecurityConfiguration;
import com.mygroup.coursework.entities.BookType;
import com.mygroup.coursework.services.BookTypeService;

@WebMvcTest(controllers = BookTypeController.class,
    excludeAutoConfiguration = SecurityAutoConfiguration.class)
@ContextConfiguration(classes = {EmptySecurityConfiguration.class, BookTypeController.class})
public class BookTypeControllerTest {
  @Autowired
  private MockMvc mvc;

  @MockBean
  private BookTypeService service;

  @Test
  public void findAllWhenNoneExist() throws Exception {
    when(service.findAll()).thenReturn(Collections.emptyList());

    mvc.perform(get("/bookTypes/findAll")).andExpect(status().isOk())
        .andExpect(content().json("[]"));
  }

  @Test
  public void findAllWhenMultipleExist() throws Exception {
    BookType bookType1 = new BookType("Comedy", 1, 2, 3, null);
    BookType bookType2 = new BookType("Drama", 4, 5, 123, null);
    List<BookType> list = List.of(bookType1, bookType2);
    when(service.findAll()).thenReturn(list);

    mvc.perform(get("/bookTypes/findAll")).andExpect(status().isOk())
        .andExpect(content().json(new ObjectMapper().writeValueAsString(list)));
  }

  @Test
  public void findByIdWhenIdDoesNotExist() throws Exception {
    when(service.findById(0)).thenThrow(NoSuchElementException.class);
    mvc.perform(get("/bookTypes/findById?id=0")).andExpect(status().isNotFound())
        .andExpect(content().string(""));
  }

  @Test
  public void findByIdWhenIdExists() throws Exception {
    BookType bookType = new BookType("Comedy", 1, 2, 3, null);
    when(service.findById(0)).thenReturn(bookType);
    mvc.perform(get("/bookTypes/findById?id=0")).andExpect(status().isOk())
        .andExpect(content().json(new ObjectMapper().writeValueAsString(bookType)));
  }

  @Test
  public void findByNameWhenNoneNotExist() throws Exception {
    when(service.findByName("abc")).thenReturn(Collections.emptyList());
    mvc.perform(get("/bookTypes/findByName?name=abc")).andExpect(status().isOk())
        .andExpect(content().json("[]"));
  }

  @Test
  public void findByNameWhenMultipleExist() throws Exception {
    BookType bookType1 = new BookType("Comedy", 1, 2, 3, null);
    BookType bookType2 = new BookType("Comedy", 4, 5, 123, null);
    List<BookType> list = List.of(bookType1, bookType2);
    when(service.findByName("Comedy")).thenReturn(list);
    mvc.perform(get("/bookTypes/findByName?name=Comedy")).andExpect(status().isOk())
        .andExpect(content().json(new ObjectMapper().writeValueAsString(list)));
  }

  @Test
  public void save() throws Exception {
    BookType bookType = new BookType("Comedy", 1, 2, 3, null);
    ObjectMapper mapper = new ObjectMapper();
    when(service.save(any(BookType.class))).thenReturn(bookType);
    mvc.perform(post("/bookTypes/save").contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(bookType))).andExpect(status().isOk())
        .andExpect(content().json(mapper.writeValueAsString(bookType)));
  }

  @Test
  public void deleteByIdWhenIdDoesNotExist() throws Exception {
    doThrow(EmptyResultDataAccessException.class).when(service).deleteById(0);
    mvc.perform(delete("/bookTypes/deleteById?id=0")).andExpect(status().isNotFound())
        .andExpect(content().string(""));
  }

  @Test
  public void deleteByIdWhenIdExists() throws Exception {
    doNothing().when(service).deleteById(0);
    mvc.perform(delete("/bookTypes/deleteById?id=0")).andExpect(status().isOk())
        .andExpect(content().string(""));
  }

  @Test
  public void deleteByName() throws Exception {
    doNothing().when(service).deleteByName("abc");
    mvc.perform(delete("/bookTypes/deleteByName?name=abc")).andExpect(status().isOk())
        .andExpect(content().string(""));
  }

  @Test
  public void deleteAll() throws Exception {
    doNothing().when(service).deleteAll();
    mvc.perform(delete("/bookTypes/deleteAll")).andExpect(status().isOk())
        .andExpect(content().string(""));
  }
}
