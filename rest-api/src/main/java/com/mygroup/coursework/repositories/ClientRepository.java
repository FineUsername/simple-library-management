package com.mygroup.coursework.repositories;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.mygroup.coursework.entities.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

  @Query("SELECT c FROM Client c WHERE c.firstName = ?1 AND c.lastName = ?2")
  public List<Client> findByFullName(String firstName, String lastName);

  @Transactional
  @Modifying
  @Query("DELETE FROM Client c WHERE c.firstName = ?1 AND c.lastName = ?2")
  public void deleteByFullName(String firstName, String lastName);

}
