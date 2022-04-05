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
import com.mygroup.coursework.entities.Client;
import com.mygroup.coursework.services.ClientService;

@WebMvcTest(controllers = ClientController.class,
    excludeAutoConfiguration = SecurityAutoConfiguration.class)
@ContextConfiguration(classes = {EmptySecurityConfiguration.class, ClientController.class})
public class ClientControllerTest {
  @Autowired
  private MockMvc mvc;

  @MockBean
  private ClientService service;

  @Test
  public void findAllWhenNoneExist() throws Exception {
    when(service.findAll()).thenReturn(Collections.emptyList());

    mvc.perform(get("/clients/findAll")).andExpect(status().isOk()).andExpect(content().json("[]"));
  }

  @Test
  public void findAllWhenMultipleExist() throws Exception {
    Client client1 = new Client("Jonh", "Doe", "Partner", "seria", "num", null);
    Client client2 = new Client("Jane", "Doeeee", "Hey", "seriaa", "num123", null);
    List<Client> list = List.of(client1, client2);
    when(service.findAll()).thenReturn(list);

    mvc.perform(get("/clients/findAll")).andExpect(status().isOk())
        .andExpect(content().json(new ObjectMapper().writeValueAsString(list)));
  }

  @Test
  public void findByIdWhenIdDoesNotExist() throws Exception {
    when(service.findById(0)).thenThrow(NoSuchElementException.class);
    mvc.perform(get("/clients/findById?id=0")).andExpect(status().isNotFound())
        .andExpect(content().string(""));
  }

  @Test
  public void findByIdWhenIdExists() throws Exception {
    Client client = new Client("Jonh", "Doe", "Partner", "seria", "num", null);
    when(service.findById(0)).thenReturn(client);
    mvc.perform(get("/clients/findById?id=0")).andExpect(status().isOk())
        .andExpect(content().json(new ObjectMapper().writeValueAsString(client)));
  }

  @Test
  public void findByFullNameWhenNoneNotExist() throws Exception {
    when(service.findByFullName("a", "b")).thenReturn(Collections.emptyList());
    mvc.perform(get("/clients/findByFullName?first_name=a&last_name=b")).andExpect(status().isOk())
        .andExpect(content().json("[]"));
  }

  @Test
  public void findByNameWhenMultipleExist() throws Exception {
    Client client1 = new Client("Jonh", "Doe", "Partner", "seria", "num", null);
    Client client2 = new Client("Jonh", "Doe", "Hey", "seriaa", "num123", null);
    List<Client> list = List.of(client1, client2);
    when(service.findByFullName("Jonh", "Doe")).thenReturn(list);
    mvc.perform(get("/clients/findByFullName?first_name=Jonh&last_name=Doe"))
        .andExpect(status().isOk())
        .andExpect(content().json(new ObjectMapper().writeValueAsString(list)));
  }

  @Test
  public void save() throws Exception {
    Client client = new Client("Jonh", "Doe", "Partner", "seria", "num", null);
    ObjectMapper mapper = new ObjectMapper();
    when(service.save(any(Client.class))).thenReturn(client);
    mvc.perform(post("/clients/save").contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(client))).andExpect(status().isOk())
        .andExpect(content().json(mapper.writeValueAsString(client)));
  }

  @Test
  public void deleteByIdWhenIdDoesNotExist() throws Exception {
    doThrow(EmptyResultDataAccessException.class).when(service).deleteById(0);
    mvc.perform(delete("/clients/deleteById?id=0")).andExpect(status().isNotFound())
        .andExpect(content().string(""));
  }

  @Test
  public void deleteByIdWhenIdExists() throws Exception {
    doNothing().when(service).deleteById(0);
    mvc.perform(delete("/clients/deleteById?id=0")).andExpect(status().isOk())
        .andExpect(content().string(""));
  }

  @Test
  public void deleteByFullName() throws Exception {
    doNothing().when(service).deleteByFullName("a", "b");
    mvc.perform(delete("/clients/deleteByFullName?first_name=a&last_name=b"))
        .andExpect(status().isOk()).andExpect(content().string(""));
  }

  @Test
  public void deleteAll() throws Exception {
    doNothing().when(service).deleteAll();
    mvc.perform(delete("/clients/deleteAll")).andExpect(status().isOk())
        .andExpect(content().string(""));
  }
}
