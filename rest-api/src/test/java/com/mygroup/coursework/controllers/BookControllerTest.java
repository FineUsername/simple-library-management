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
import com.mygroup.coursework.entities.Book;
import com.mygroup.coursework.entities.BookType;
import com.mygroup.coursework.services.BookService;

@WebMvcTest(controllers = BookController.class,
    excludeAutoConfiguration = SecurityAutoConfiguration.class)
@ContextConfiguration(classes = {EmptySecurityConfiguration.class, BookController.class})
public class BookControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private BookService service;

  @Test
  public void findAllWhenNoneExist() throws Exception {
    when(service.findAll()).thenReturn(Collections.emptyList());

    mvc.perform(get("/books/findAll")).andExpect(status().isOk()).andExpect(content().json("[]"));
  }

  @Test
  public void findAllWhenMultipleExist() throws Exception {
    BookType bookType = new BookType("Comedy", 1, 2, 3, null);
    Book book1 = new Book("Title1", 1, bookType, null);
    Book book2 = new Book("Title2", 84, bookType, null);
    List<Book> list = List.of(book1, book2);
    when(service.findAll()).thenReturn(list);

    mvc.perform(get("/books/findAll")).andExpect(status().isOk())
        .andExpect(content().json(new ObjectMapper().writeValueAsString(list)));
  }

  @Test
  public void findByIdWhenIdDoesNotExist() throws Exception {
    when(service.findById(0)).thenThrow(NoSuchElementException.class);
    mvc.perform(get("/books/findById?id=0")).andExpect(status().isNotFound())
        .andExpect(content().string(""));
  }

  @Test
  public void findByIdWhenIdExists() throws Exception {
    BookType bookType = new BookType("Comedy", 1, 2, 3, null);
    Book book = new Book("Title1", 1, bookType, null);
    when(service.findById(0)).thenReturn(book);
    mvc.perform(get("/books/findById?id=0")).andExpect(status().isOk())
        .andExpect(content().json(new ObjectMapper().writeValueAsString(book)));
  }

  @Test
  public void findByNameWhenNoneNotExist() throws Exception {
    when(service.findByName("abc")).thenReturn(Collections.emptyList());
    mvc.perform(get("/books/findByName?name=abc")).andExpect(status().isOk())
        .andExpect(content().json("[]"));
  }

  @Test
  public void findByNameWhenMultipleExist() throws Exception {
    BookType bookType = new BookType("Comedy", 1, 2, 3, null);
    Book book1 = new Book("Title1", 1, bookType, null);
    Book book2 = new Book("Title2", 84, bookType, null);
    List<Book> list = List.of(book1, book2);
    when(service.findByName("abc")).thenReturn(list);
    mvc.perform(get("/books/findByName?name=abc")).andExpect(status().isOk())
        .andExpect(content().json(new ObjectMapper().writeValueAsString(list)));
  }

  @Test
  public void save() throws Exception {
    BookType bookType = new BookType("Comedy", 1, 2, 3, null);
    Book book = new Book("Title1", 1, bookType, null);
    ObjectMapper mapper = new ObjectMapper();
    when(service.save(any(Book.class))).thenReturn(book);
    mvc.perform(post("/books/save").contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(book))).andExpect(status().isOk())
        .andExpect(content().json(mapper.writeValueAsString(book)));
  }

  @Test
  public void deleteByIdWhenIdDoesNotExist() throws Exception {
    doThrow(EmptyResultDataAccessException.class).when(service).deleteById(0);
    mvc.perform(delete("/books/deleteById?id=0")).andExpect(status().isNotFound())
        .andExpect(content().string(""));
  }

  @Test
  public void deleteByIdWhenIdExists() throws Exception {
    doNothing().when(service).deleteById(0);
    mvc.perform(delete("/books/deleteById?id=0")).andExpect(status().isOk())
        .andExpect(content().string(""));
  }

  @Test
  public void deleteByName() throws Exception {
    doNothing().when(service).deleteByName("abc");
    mvc.perform(delete("/books/deleteByName?name=abc")).andExpect(status().isOk())
        .andExpect(content().string(""));
  }

  @Test
  public void deleteAll() throws Exception {
    doNothing().when(service).deleteAll();
    mvc.perform(delete("/books/deleteAll")).andExpect(status().isOk())
        .andExpect(content().string(""));
  }
}
