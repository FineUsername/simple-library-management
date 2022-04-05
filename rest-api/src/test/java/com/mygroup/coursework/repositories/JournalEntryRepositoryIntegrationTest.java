package com.mygroup.coursework.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import com.mygroup.coursework.entities.Book;
import com.mygroup.coursework.entities.BookType;
import com.mygroup.coursework.entities.Client;
import com.mygroup.coursework.entities.JournalEntry;

@DataJpaTest
public class JournalEntryRepositoryIntegrationTest {
  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private JournalEntryRepository repository;

  private JournalEntry entry1;
  private JournalEntry entry2;

  @BeforeEach
  void setupDatabase() {
    BookType bookType = new BookType("Drama", 1, 2, 3, null);
    Book book = new Book("Title", 5, bookType, null);
    Client client = new Client("Kely", "Kris", "Leo", "ser", "num", null);
    entry1 = new JournalEntry(book, client, new Timestamp(new Date().getTime()), null, null);
    entry2 = new JournalEntry(book, client, new Timestamp(new Date().getTime()), null, null);
    entityManager.persist(client);
    entityManager.persist(bookType);
    entityManager.persist(book);
    entityManager.persist(entry1);
    entityManager.persist(entry2);
    entityManager.flush();
  }

  @Test
  public void findByBookNameWhenMultipleExist() {
    List<JournalEntry> found = repository.findByBookName(entry1.getBook().getName());
    assertNotNull(found);
    assertEquals(found.size(), 2);
    assertSame(found.get(0), entry1);
    assertSame(found.get(1), entry2);
  }

  @Test
  public void findByBookNameWhenNoneExist() {
    List<JournalEntry> found = repository.findByBookName("Name that isn't presented in db");
    assertNotNull(found);
    assertTrue(found.isEmpty());
  }

  @Test
  public void findByClientFirstNameWhenMultipleExist() {
    List<JournalEntry> found = repository.findByClientFirstName(entry1.getClient().getFirstName());
    assertNotNull(found);
    assertEquals(found.size(), 2);
    assertSame(found.get(0), entry1);
    assertSame(found.get(1), entry2);
  }

  @Test
  public void findByClientFirstNameWhenNoneExist() {
    List<JournalEntry> found = repository.findByClientFirstName("Name that isn't presented in db");
    assertNotNull(found);
    assertTrue(found.isEmpty());
  }

}
