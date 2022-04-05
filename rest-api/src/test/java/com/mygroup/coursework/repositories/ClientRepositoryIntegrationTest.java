package com.mygroup.coursework.repositories;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import com.mygroup.coursework.entities.Client;

@DataJpaTest
public class ClientRepositoryIntegrationTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private ClientRepository repository;

  private Client client1;
  private Client client2;

  @BeforeEach
  void setupDatabase() {
    client1 = new Client("John", "Jake", "Abcde", "seria", "num", null);
    client2 = new Client("John", "Jake", "Another partner", "seria", "num", null);
    entityManager.persist(client1);
    entityManager.persist(client2);
    entityManager.flush();
  }

  @Test
  public void findByFullNameWhenMultipleExist() {
    List<Client> found = repository.findByFullName(client1.getFirstName(), client2.getLastName());
    assertNotNull(found);
    assertEquals(found.size(), 2);
    assertSame(found.get(0), client1);
    assertSame(found.get(1), client2);
  }

  @Test
  public void findByFullNameWhenNoneExist() {
    List<Client> found = repository.findByFullName("Unreal first name", "Unreal last name");
    assertNotNull(found);
    assertTrue(found.isEmpty());
  }

  @Test
  public void deleteByFullNameWhenMultipleExist() {
    assertFalse(repository.findByFullName(client1.getFirstName(), client1.getLastName()).isEmpty());
    repository.deleteByFullName(client1.getFirstName(), client1.getLastName());
    assertTrue(repository.findByFullName(client1.getFirstName(), client1.getLastName()).isEmpty());
  }

  @Test
  public void deleteByFullNameWhenNoneExist() {
    String firstName = "Name that isn't presented in db";
    String lastName = "Unreal name";
    assertTrue(repository.findByFullName(firstName, lastName).isEmpty());
    repository.deleteByFullName(firstName, lastName);
    assertTrue(repository.findByFullName(firstName, lastName).isEmpty());
  }
}
