package com.mygroup.coursework.repositories;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import com.mygroup.coursework.entities.BookType;

@DataJpaTest
public class BookTypeRepositoryIntegrationTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private BookTypeRepository repository;

  private BookType bookType1;
  private BookType bookType2;

  @BeforeEach
  void setupDatabase() {
    bookType1 = new BookType("Title", 123, 321, 1, null);
    bookType2 = new BookType("Title", 4, 5, 6, null);
    entityManager.persist(bookType1);
    entityManager.persist(bookType2);
    entityManager.flush();
  }

  @Test
  public void findByNameWhenMultipleExist() {
    List<BookType> found = repository.findByName(bookType1.getName());
    assertNotNull(found);
    assertEquals(found.size(), 2);
    assertSame(found.get(0), bookType1);
    assertSame(found.get(1), bookType2);
  }

  @Test
  public void findByNameWhenNoneExist() {
    List<BookType> found = repository.findByName("Name that isn't presented in db");
    assertNotNull(found);
    assertTrue(found.isEmpty());
  }

  @Test
  public void deleteByNameWhenMultipleExist() {
    assertFalse(repository.findByName(bookType1.getName()).isEmpty());
    repository.deleteByName(bookType1.getName());
    assertTrue(repository.findByName(bookType1.getName()).isEmpty());
  }

  @Test
  public void deleteByNameWhenNoneExist() {
    String name = "Name that isn't presented in db";
    assertTrue(repository.findByName(name).isEmpty());
    repository.deleteByName(name);
    assertTrue(repository.findByName(name).isEmpty());
  }
}
