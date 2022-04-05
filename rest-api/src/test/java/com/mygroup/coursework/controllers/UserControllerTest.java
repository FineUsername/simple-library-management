package com.mygroup.coursework.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mygroup.coursework.EmptySecurityConfiguration;
import com.mygroup.coursework.entities.User;
import com.mygroup.coursework.security.jwt.JwtProperties;
import com.mygroup.coursework.security.jwt.JwtTokenProvider;
import com.mygroup.coursework.services.JwtUserService;

@WebMvcTest(controllers = UserController.class,
    excludeAutoConfiguration = SecurityAutoConfiguration.class)
@ContextConfiguration(classes = {EmptySecurityConfiguration.class, UserController.class,
    JwtTokenProvider.class, JwtProperties.class})
public class UserControllerTest {
  @Autowired
  private MockMvc mvc;

  @MockBean
  private JwtUserService service;

  @Test
  public void signInUsernameDoesntExistInDb() throws Exception {
    User user = new User("usernameThatIsNotPresentInDb", "password", null);
    assertThrows(NestedServletException.class,
        () -> mvc
            .perform(post("/users/signIn").contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(user)))
            .andExpect(status().isOk()).andExpect(jsonPath("$.username", is(user.getUsername())))
            .andExpect(jsonPath("$.token").exists()));
  }

  @Test
  public void signUpWhenUserAlreadyExists() throws Exception {
    when(service.usernameExists("username")).thenReturn(true);
    User user = new User("username", "password", null);
    assertThrows(NestedServletException.class,
        () -> mvc.perform(post("/users/signUp").contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(user))));
  }

  @Test
  public void signUpWhenUserDoesNotExist() throws Exception {
    User user = new User("username", "password", null);
    when(service.usernameExists("username")).thenReturn(false);
    when(service.save(any(User.class))).thenReturn(user);

    mvc.perform(post("/users/signUp").contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(user))).andExpect(status().isOk())
        .andExpect(content().json(new ObjectMapper().writeValueAsString(user)));
  }

  @Test
  public void findAllWhenNoneExist() throws Exception {
    when(service.findAll()).thenReturn(Collections.emptyList());
    mvc.perform(get("/users/findAll")).andExpect(status().isOk()).andExpect(content().json("[]"));
  }

  @Test
  public void findAllWhenMultipleExist() throws Exception {
    User user1 = new User("username1", "password1", null);
    User user2 = new User("username2", "password2", null);
    List<User> list = List.of(user1, user2);
    when(service.findAll()).thenReturn(list);

    mvc.perform(get("/users/findAll")).andExpect(status().isOk())
        .andExpect(content().json(new ObjectMapper().writeValueAsString(list)));
  }

  @Test
  public void deleteByIdWhenIdDoesNotExist() throws Exception {
    doThrow(EmptyResultDataAccessException.class).when(service).deleteById(0);
    mvc.perform(delete("/users/deleteById?id=0")).andExpect(status().isNotFound())
        .andExpect(content().string(""));
  }

  @Test
  public void deleteByIdWhenIdExists() throws Exception {
    doNothing().when(service).deleteById(0);
    mvc.perform(delete("/users/deleteById?id=0")).andExpect(status().isOk())
        .andExpect(content().string(""));
  }
}
