package com.mygroup.coursework.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mygroup.coursework.entities.BookType;
import com.mygroup.coursework.repositories.BookTypeRepository;

@Service
public class BookTypeService {

  private final BookTypeRepository repository;

  @Autowired
  public BookTypeService(BookTypeRepository repository) {
    this.repository = repository;
  }

  public List<BookType> findAll() {
    return repository.findAll();
  }

  public BookType findById(long id) {
    return repository.findById(id).orElseThrow();
  }

  public List<BookType> findByName(String name) {
    return repository.findByName(name);
  }

  public BookType save(BookType bookType) {
    return repository.save(bookType);
  }

  public void deleteById(long id) {
    repository.deleteById(id);
  }

  public void deleteByName(String name) {
    repository.deleteByName(name);
  }

  public void deleteAll() {
    repository.deleteAll();
  }
}
