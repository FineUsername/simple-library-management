package com.mygroup.coursework.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mygroup.coursework.entities.JournalEntry;
import com.mygroup.coursework.repositories.JournalEntryRepository;

@Service
public class JournalEntryService {

  private final JournalEntryRepository repository;

  @Autowired
  public JournalEntryService(JournalEntryRepository repository) {
    this.repository = repository;
  }

  public List<JournalEntry> findAll() {
    return repository.findAll();
  }

  public JournalEntry findById(long id) {
    return repository.findById(id).orElseThrow();
  }

  public List<JournalEntry> findByBookName(String bookName) {
    return repository.findByBookName(bookName);
  }

  public List<JournalEntry> findByClientFirstName(String firstName) {
    return repository.findByClientFirstName(firstName);
  }

  public JournalEntry save(JournalEntry book) {
    return repository.save(book);
  }

  public void deleteById(long id) {
    repository.deleteById(id);
  }

  public void deleteByBookName(String bookName) {
    repository.deleteByBookName(bookName);
  }

  public void deleteByClientFirstName(String firstName) {
    repository.deleteByClientFirstName(firstName);
  }

  public void deleteAll() {
    repository.deleteAll();
  }
}
