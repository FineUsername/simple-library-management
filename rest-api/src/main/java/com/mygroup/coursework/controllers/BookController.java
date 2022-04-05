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
import com.mygroup.coursework.entities.Book;
import com.mygroup.coursework.services.BookService;

@RestController
@RequestMapping(path = "books")
public class BookController {

  private final BookService service;

  @Autowired
  public BookController(BookService bookService) {
    this.service = bookService;
  }

  @GetMapping("findAll")
  public ResponseEntity<List<Book>> findAll() {
    return ResponseEntity.ok(service.findAll());
  }

  @GetMapping("findById")
  public ResponseEntity<Book> findById(@RequestParam("id") long id) {
    try {
      return ResponseEntity.ok(service.findById(id));
    } catch (NoSuchElementException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("findByName")
  public ResponseEntity<List<Book>> findByName(@RequestParam("name") String name) {
    return ResponseEntity.ok(service.findByName(name));
  }

  @PostMapping("save")
  public ResponseEntity<Book> save(@RequestBody Book book) {
    return ResponseEntity.ok(service.save(book));
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
