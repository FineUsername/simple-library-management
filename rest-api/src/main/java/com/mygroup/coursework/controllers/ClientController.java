package com.mygroup.coursework.controllers;

import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.mygroup.coursework.entities.Client;
import com.mygroup.coursework.services.ClientService;

@RestController
@RequestMapping("clients")
public class ClientController {
  private final ClientService service;

  @Autowired
  public ClientController(ClientService service) {
    this.service = service;
  }

  @GetMapping("findAll")
  public ResponseEntity<List<Client>> findAll() {
    return ResponseEntity.ok(service.findAll());
  }

  @GetMapping("findById")
  public ResponseEntity<Client> findById(@RequestParam("id") long id) {
    try {
      return ResponseEntity.ok(service.findById(id));
    } catch (NoSuchElementException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("findByFullName")
  public ResponseEntity<List<Client>> findByFullName(@RequestParam("first_name") String firstName,
      @RequestParam("last_name") String lastName) {
    return ResponseEntity.ok(service.findByFullName(firstName, lastName));
  }

  @PostMapping("save")
  public ResponseEntity<Client> save(@RequestBody Client client) {
    return ResponseEntity.ok(service.save(client));
  }

  @DeleteMapping("deleteById")
  public ResponseEntity<Object> deleteById(@RequestParam("id") long id) {
    try {
      service.deleteById(id);
      return ResponseEntity.ok().build();
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    } catch (EmptyResultDataAccessException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("deleteByFullName")
  public ResponseEntity<Object> deleteByFullName(@RequestParam("first_name") String firstName,
      @RequestParam("last_name") String lastName) {
    service.deleteByFullName(firstName, lastName);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("deleteAll")
  public ResponseEntity<Object> deleteAll() {
    service.deleteAll();
    return ResponseEntity.ok().build();
  }
}
