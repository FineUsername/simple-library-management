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
public class UserMappingsAccessTest {
  @Autowired
  private WebApplicationContext context;

  private MockMvc mvc;

  @BeforeEach
  public void setup() {
    mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
  }

  @Test
  public void signInUnauthenticated() throws Exception {
    mvc.perform(post("/users/signIn").contentType(MediaType.APPLICATION_JSON)
        .content("some deserialized user")).andExpect(status().isBadRequest());
  }

  @Test
  @WithMockUser(roles = "USER")
  public void signInAsUser() throws Exception {
    mvc.perform(post("/users/signIn").contentType(MediaType.APPLICATION_JSON)
        .content("some deserialized user")).andExpect(status().isBadRequest());
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  public void signInAsAdmin() throws Exception {
    mvc.perform(post("/users/signIn").contentType(MediaType.APPLICATION_JSON)
        .content("some deserialized user")).andExpect(status().isBadRequest());
  }

  @Test
  public void signUpUnauthenticated() throws Exception {
    mvc.perform(post("/users/signUp").contentType(MediaType.APPLICATION_JSON)
        .content("some deserialized user")).andExpect(status().isBadRequest());
  }

  @Test
  @WithMockUser(roles = "USER")
  public void signUpAsUser() throws Exception {
    mvc.perform(post("/users/signUp").contentType(MediaType.APPLICATION_JSON)
        .content("some deserialized user")).andExpect(status().isBadRequest());
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  public void signUpAsAdmin() throws Exception {
    mvc.perform(post("/users/signUp").contentType(MediaType.APPLICATION_JSON)
        .content("some deserialized user")).andExpect(status().isBadRequest());
  }

  @Test
  public void findAllUnauthenticated() throws Exception {
    mvc.perform(get("/users/findAll")).andExpect(status().isUnauthorized());
  }

  @Test
  @WithMockUser(roles = "USER")
  public void findAllAsUser() throws Exception {
    mvc.perform(get("/users/findAll")).andExpect(status().isOk());
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  public void findAllAsAdmin() throws Exception {
    mvc.perform(get("/users/findAll")).andExpect(status().isOk());
  }

  @Test
  public void deleteByIdUnauthenticated() throws Exception {
    mvc.perform(delete("/users/deleteById?id=0")).andExpect(status().isUnauthorized());
  }

  @Test
  @WithMockUser(roles = "USER")
  public void deleteByIdAsUser() throws Exception {
    mvc.perform(delete("/users/deleteById?id=0")).andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  public void deleteByIdAsAdmin() throws Exception {
    mvc.perform(delete("/users/deleteById?id=0")).andExpect(status().isNotFound());
  }
}
