package com.mygroup.coursework.security;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
public class BookMappingAccessTest {

  @Autowired
  private WebApplicationContext context;

  private MockMvc mvc;

  @BeforeEach
  public void setup() {
    mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
  }

  @Test
  public void findAllUnauthenticated() throws Exception {
    mvc.perform(get("/books/findAll")).andExpect(status().isUnauthorized());
  }

  @Test
  @WithMockUser(roles = "USER")
  public void findAllAsUser() throws Exception {
    mvc.perform(get("/books/findAll")).andExpect(status().isOk());
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  public void findAllAsAdmin() throws Exception {
    mvc.perform(get("/books/findAll")).andExpect(status().isOk());
  }

  @Test
  public void findByIdUnauthenticated() throws Exception {
    mvc.perform(get("/books/findById?id=0")).andExpect(status().isUnauthorized());
  }

  @Test
  @WithMockUser(roles = "USER")
  public void findByIdAsUser() throws Exception {
    mvc.perform(get("/books/findById?id=0")).andExpect(status().isNotFound());
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  public void findByIdAsAdmin() throws Exception {
    mvc.perform(get("/books/findById?id=0")).andExpect(status().isNotFound());
  }

  @Test
  public void findByNameUnauthenticated() throws Exception {
    mvc.perform(get("/books/findByName?name=abc")).andExpect(status().isUnauthorized());
  }

  @Test
  @WithMockUser(roles = "USER")
  public void findByNameAsUser() throws Exception {
    mvc.perform(get("/books/findByName?name=abc")).andExpect(status().isOk());
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  public void findByNameAsAdmin() throws Exception {
    mvc.perform(get("/books/findByName?name=abc")).andExpect(status().isOk());
  }

  @Test
  public void saveUnauthenticated() throws Exception {
    mvc.perform(post("/books/save").contentType(MediaType.APPLICATION_JSON)
        .content("some deserialized book")).andExpect(status().isUnauthorized());
  }

  @Test
  @WithMockUser(roles = "USER")
  public void saveAsUser() throws Exception {
    mvc.perform(post("/books/save").contentType(MediaType.APPLICATION_JSON)
        .content("some deserialized book")).andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  public void saveAsAdmin() throws Exception {
    mvc.perform(post("/books/save").contentType(MediaType.APPLICATION_JSON)
        .content("some deserialized book")).andExpect(status().isBadRequest());
  }

  @Test
  public void deleteByIdUnauthenticated() throws Exception {
    mvc.perform(delete("/books/deleteById?id=0")).andExpect(status().isUnauthorized());
  }

  @Test
  @WithMockUser(roles = "USER")
  public void deleteByIdAsUser() throws Exception {
    mvc.perform(delete("/books/deleteById?id=0")).andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  public void deleteByIdAsAdmin() throws Exception {
    mvc.perform(delete("/books/deleteById?id=0")).andExpect(status().isNotFound());
  }

  @Test
  public void deleteByNameUnauthenticated() throws Exception {
    mvc.perform(delete("/books/deleteByName?name=abc")).andExpect(status().isUnauthorized());
  }

  @Test
  @WithMockUser(roles = "USER")
  public void deleteByNameAsUser() throws Exception {
    mvc.perform(delete("/books/deleteByName?name=abc")).andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  public void deleteByNameAsAdmin() throws Exception {
    mvc.perform(delete("/books/deleteByName?name=abc")).andExpect(status().isOk());
  }

  @Test
  public void deleteAllUnauthenticated() throws Exception {
    mvc.perform(delete("/books/deleteAll")).andExpect(status().isUnauthorized());
  }

  @Test
  @WithMockUser(roles = "USER")
  public void deleteAllAsUser() throws Exception {
    mvc.perform(delete("/books/deleteAll")).andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  public void deleteAllAsAdmin() throws Exception {
    mvc.perform(delete("/books/deleteAll")).andExpect(status().isOk());
  }

}
