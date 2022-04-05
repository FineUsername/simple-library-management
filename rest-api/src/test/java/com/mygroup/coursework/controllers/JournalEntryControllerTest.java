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
import com.mygroup.coursework.entities.Client;
import com.mygroup.coursework.entities.JournalEntry;
import com.mygroup.coursework.services.JournalEntryService;

@WebMvcTest(controllers = JournalEntryController.class,
    excludeAutoConfiguration = SecurityAutoConfiguration.class)
@ContextConfiguration(classes = {EmptySecurityConfiguration.class, JournalEntryController.class})
public class JournalEntryControllerTest {
  @Autowired
  private MockMvc mvc;

  @MockBean
  private JournalEntryService service;

  @Test
  public void findAllWhenNoneExist() throws Exception {
    when(service.findAll()).thenReturn(Collections.emptyList());

    mvc.perform(get("/journal/findAll")).andExpect(status().isOk()).andExpect(content().json("[]"));
  }

  @Test
  public void findAllWhenMultipleExist() throws Exception {
    BookType bookType = new BookType("Comedy", 1, 2, 3, null);
    Book book1 = new Book("Title1", 1, bookType, null);
    Book book2 = new Book("Title2", 84, bookType, null);
    Client client1 = new Client("Jonh", "Doe", "Partner", "seria", "num", null);
    Client client2 = new Client("Jane", "Doe", "ally", "ee", "5566", null);
    JournalEntry entry1 = new JournalEntry(book1, client1, null, null, null);
    JournalEntry entry2 = new JournalEntry(book2, client2, null, null, null);
    List<JournalEntry> list = List.of(entry1, entry2);
    when(service.findAll()).thenReturn(list);

    mvc.perform(get("/journal/findAll")).andExpect(status().isOk())
        .andExpect(content().json(new ObjectMapper().writeValueAsString(list)));
  }

  @Test
  public void findByIdWhenIdDoesNotExist() throws Exception {
    when(service.findById(0)).thenThrow(NoSuchElementException.class);
    mvc.perform(get("/journal/findById?id=0")).andExpect(status().isNotFound())
        .andExpect(content().string(""));
  }

  @Test
  public void findByIdWhenIdExists() throws Exception {
    BookType bookType = new BookType("Comedy", 1, 2, 3, null);
    Book book = new Book("Title1", 1, bookType, null);
    Client client = new Client("Jonh", "Doe", "Partner", "seria", "num", null);
    JournalEntry entry = new JournalEntry(book, client, null, null, null);
    when(service.findById(0)).thenReturn(entry);
    mvc.perform(get("/journal/findById?id=0")).andExpect(status().isOk())
        .andExpect(content().json(new ObjectMapper().writeValueAsString(entry)));
  }

  @Test
  public void findByBookNameWhenNoneNotExist() throws Exception {
    when(service.findByBookName("abc")).thenReturn(Collections.emptyList());
    mvc.perform(get("/journal/findByBookName?bookName=abc")).andExpect(status().isOk())
        .andExpect(content().json("[]"));
  }

  @Test
  public void findByBookNameWhenMultipleExist() throws Exception {
    BookType bookType = new BookType("Comedy", 1, 2, 3, null);
    Book book1 = new Book("Title1", 1, bookType, null);
    Book book2 = new Book("Title1", 84, bookType, null);
    Client client1 = new Client("Jonh", "Doe", "Partner", "seria", "num", null);
    Client client2 = new Client("Jane", "Doe", "ally", "ee", "5566", null);
    JournalEntry entry1 = new JournalEntry(book1, client1, null, null, null);
    JournalEntry entry2 = new JournalEntry(book2, client2, null, null, null);
    List<JournalEntry> list = List.of(entry1, entry2);
    when(service.findByBookName("Title1")).thenReturn(list);
    mvc.perform(get("/journal/findByBookName?bookName=Title1")).andExpect(status().isOk())
        .andExpect(content().json(new ObjectMapper().writeValueAsString(list)));
  }

  @Test
  public void findByClientFirstNameWhenNoneNotExist() throws Exception {
    when(service.findByClientFirstName("abc")).thenReturn(Collections.emptyList());
    mvc.perform(get("/journal/findByClientFirstName?firstName=abc")).andExpect(status().isOk())
        .andExpect(content().json("[]"));
  }

  @Test
  public void findByClientFirstNameWhenMultipleExist() throws Exception {
    BookType bookType = new BookType("Comedy", 1, 2, 3, null);
    Book book1 = new Book("Title1", 1, bookType, null);
    Book book2 = new Book("Title2", 84, bookType, null);
    Client client1 = new Client("Jonh", "Doe", "Partner", "seria", "num", null);
    Client client2 = new Client("Jonh", "Lee", "ally", "ee", "5566", null);
    JournalEntry entry1 = new JournalEntry(book1, client1, null, null, null);
    JournalEntry entry2 = new JournalEntry(book2, client2, null, null, null);
    List<JournalEntry> list = List.of(entry1, entry2);
    when(service.findByClientFirstName("Jonh")).thenReturn(list);
    mvc.perform(get("/journal/findByClientFirstName?firstName=Jonh")).andExpect(status().isOk())
        .andExpect(content().json(new ObjectMapper().writeValueAsString(list)));
  }

  @Test
  public void save() throws Exception {
    BookType bookType = new BookType("Comedy", 1, 2, 3, null);
    Book book = new Book("Title1", 1, bookType, null);
    Client client = new Client("Jonh", "Doe", "Partner", "seria", "num", null);
    JournalEntry entry = new JournalEntry(book, client, null, null, null);
    ObjectMapper mapper = new ObjectMapper();
    when(service.save(any(JournalEntry.class))).thenReturn(entry);
    mvc.perform(post("/journal/save").contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(entry))).andExpect(status().isOk())
        .andExpect(content().json(mapper.writeValueAsString(entry)));
  }

  @Test
  public void deleteByIdWhenIdDoesNotExist() throws Exception {
    doThrow(EmptyResultDataAccessException.class).when(service).deleteById(0);
    mvc.perform(delete("/journal/deleteById?id=0")).andExpect(status().isNotFound())
        .andExpect(content().string(""));
  }

  @Test
  public void deleteByIdWhenIdExists() throws Exception {
    doNothing().when(service).deleteById(0);
    mvc.perform(delete("/journal/deleteById?id=0")).andExpect(status().isOk())
        .andExpect(content().string(""));
  }

  @Test
  public void deleteByBookName() throws Exception {
    doNothing().when(service).deleteByBookName("abc");
    mvc.perform(delete("/journal/deleteByBookName?bookName=abc")).andExpect(status().isOk())
        .andExpect(content().string(""));
  }

  @Test
  public void deleteByClientFirstName() throws Exception {
    doNothing().when(service).deleteByClientFirstName("abc");
    mvc.perform(delete("/journal/deleteByClientFirstName?firstName=abc")).andExpect(status().isOk())
        .andExpect(content().string(""));
  }

  @Test
  public void deleteAll() throws Exception {
    doNothing().when(service).deleteAll();
    mvc.perform(delete("/journal/deleteAll")).andExpect(status().isOk())
        .andExpect(content().string(""));
  }
}
