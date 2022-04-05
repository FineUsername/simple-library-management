package com.mygroup.coursework.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.mygroup.coursework.entities.User;
import com.mygroup.coursework.security.jwt.JwtTokenProvider;
import com.mygroup.coursework.services.JwtUserService;

@RestController
@RequestMapping("users")
public class UserController {

  private final JwtUserService service;
  private final JwtTokenProvider jwtTokenProvider;

  @Autowired
  public UserController(JwtUserService service, JwtTokenProvider jwtTokenProvider) {
    this.service = service;
    this.jwtTokenProvider = jwtTokenProvider;
  }

  @PostMapping("signIn")
  public ResponseEntity<Map<Object, Object>> signIn(@RequestBody User user) {
    String username = user.getUsername();
    if (!service.usernameExists(username)) {
      throw new UsernameNotFoundException(String.format("User %s not found", username));
    }
    try {
      String token = jwtTokenProvider.createToken(username);
      Map<Object, Object> body = new HashMap<>();
      body.put("username", username);
      body.put("token", token);
      return ResponseEntity.ok(body);
    } catch (AuthenticationException e) {
      throw new BadCredentialsException("Invalid username or password", e);
    }
  }

  @PostMapping("signUp")
  public ResponseEntity<User> signUp(@RequestBody User user) {
    if (service.usernameExists(user.getUsername())) {
      throw new IllegalArgumentException(
          String.format("User with username %s already exists", user.getUsername()));
    }
    return ResponseEntity.ok(service.save(user));
  }

  @GetMapping("findAll")
  public ResponseEntity<List<User>> findAll() {
    return ResponseEntity.ok(service.findAll());
  }

  @DeleteMapping("deleteById")
  public ResponseEntity<Object> deleteById(@RequestParam long id) {
    try {
      service.deleteById(id);
      return ResponseEntity.ok().build();
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    } catch (EmptyResultDataAccessException e) {
      return ResponseEntity.notFound().build();
    }
  }
}
