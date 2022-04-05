package com.mygroup.coursework.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mygroup.coursework.entities.Book;
import com.mygroup.coursework.repositories.BookRepository;

@Service
public class BookService {

  private final BookRepository repository;

  @Autowired
  public BookService(BookRepository bookRepository) {
    this.repository = bookRepository;
  }

  public List<Book> findAll() {
    return repository.findAll();
  }

  public Book findById(long id) {
    return repository.findById(id).orElseThrow();
  }

  public List<Book> findByName(String name) {
    return repository.findByName(name);
  }

  public Book save(Book book) {
    return repository.save(book);
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
