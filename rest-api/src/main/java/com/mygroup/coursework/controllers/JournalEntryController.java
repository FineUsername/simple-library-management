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
import com.mygroup.coursework.entities.JournalEntry;
import com.mygroup.coursework.services.JournalEntryService;

@RestController
@RequestMapping("journal")
public class JournalEntryController {

  private final JournalEntryService service;

  @Autowired
  public JournalEntryController(JournalEntryService service) {
    this.service = service;
  }

  @GetMapping("findAll")
  public ResponseEntity<List<JournalEntry>> findAll() {
    return ResponseEntity.ok(service.findAll());
  }

  @GetMapping("findById")
  public ResponseEntity<JournalEntry> findById(@RequestParam("id") long id) {
    try {
      return ResponseEntity.ok(service.findById(id));
    } catch (NoSuchElementException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("findByBookName")
  public ResponseEntity<List<JournalEntry>> findByBookName(@RequestParam("bookName") String name) {
    return ResponseEntity.ok(service.findByBookName(name));
  }

  @GetMapping("findByClientFirstName")
  public ResponseEntity<List<JournalEntry>> findByClientFirstName(
      @RequestParam("firstName") String name) {
    return ResponseEntity.ok(service.findByClientFirstName(name));
  }

  @PostMapping("save")
  public ResponseEntity<JournalEntry> save(@RequestBody JournalEntry entry) {
    return ResponseEntity.ok(service.save(entry));
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

  @DeleteMapping("deleteByBookName")
  public ResponseEntity<Object> deleteByBookName(@RequestParam("bookName") String name) {
    service.deleteByBookName(name);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("deleteByClientFirstName")
  public ResponseEntity<Object> deleteByClientFirstName(@RequestParam("firstName") String name) {
    service.deleteByClientFirstName(name);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("deleteAll")
  public ResponseEntity<Object> deleteAll() {
    service.deleteAll();
    return ResponseEntity.ok().build();
  }
}
