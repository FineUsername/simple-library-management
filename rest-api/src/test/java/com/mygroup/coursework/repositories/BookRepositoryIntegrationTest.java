package com.mygroup.coursework.repositories;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import com.mygroup.coursework.entities.Book;
import com.mygroup.coursework.entities.BookType;

@DataJpaTest
public class BookRepositoryIntegrationTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private BookRepository repository;

  private Book book1;
  private Book book2;

  @BeforeEach
  void setupDatabase() {
    BookType bookType = new BookType("Fiction", 1, 2, 3, null);
    book1 = new Book("Title", 123, bookType, null);
    book2 = new Book("Title", 48, bookType, null);
    bookType.setBooks(List.of(book1, book2));
    entityManager.persist(bookType);
    entityManager.persist(book1);
    entityManager.persist(book2);
    entityManager.flush();
  }

  @Test
  public void findByNameWhenMultipleExist() {
    List<Book> found = repository.findByName(book1.getName());
    assertNotNull(found);
    assertEquals(found.size(), 2);
    assertSame(found.get(0), book1);
    assertSame(found.get(1), book2);
  }

  @Test
  public void findByNameWhenNoneExist() {
    List<Book> found = repository.findByName("Name that isn't presented in db");
    assertNotNull(found);
    assertTrue(found.isEmpty());
  }

  @Test
  public void deleteByNameWhenMultipleExist() {
    assertFalse(repository.findByName(book1.getName()).isEmpty());
    repository.deleteByName(book1.getName());
    assertTrue(repository.findByName(book1.getName()).isEmpty());
  }

  @Test
  public void deleteByNameWhenNoneExist() {
    String name = "Name that isn't presented in db";
    assertTrue(repository.findByName(name).isEmpty());
    repository.deleteByName(name);
    assertTrue(repository.findByName(name).isEmpty());
  }
}
