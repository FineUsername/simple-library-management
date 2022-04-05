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
import com.mygroup.coursework.entities.BookType;
import com.mygroup.coursework.services.BookTypeService;

@RestController
@RequestMapping("bookTypes")
public class BookTypeController {

  private final BookTypeService service;

  @Autowired
  public BookTypeController(BookTypeService service) {
    this.service = service;
  }

  @GetMapping("findAll")
  public ResponseEntity<List<BookType>> findAll() {
    return ResponseEntity.ok(service.findAll());
  }

  @GetMapping("findById")
  public ResponseEntity<BookType> findById(@RequestParam("id") long id) {
    try {
      return ResponseEntity.ok(service.findById(id));
    } catch (NoSuchElementException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("findByName")
  public ResponseEntity<List<BookType>> findByName(@RequestParam("name") String name) {
    return ResponseEntity.ok(service.findByName(name));
  }

  @PostMapping("save")
  public ResponseEntity<BookType> save(@RequestBody BookType bookType) {
    return ResponseEntity.ok(service.save(bookType));
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

  @DeleteMapping("deleteByName")
  public ResponseEntity<Object> deleteByName(@RequestParam("name") String name) {
    service.deleteByName(name);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("deleteAll")
  public ResponseEntity<Object> deleteAll() {
    service.deleteAll();
    return ResponseEntity.ok().build();
  }
}
