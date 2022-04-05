package com.mygroup.coursework.repositories;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.mygroup.coursework.entities.JournalEntry;

@Repository
public interface JournalEntryRepository extends JpaRepository<JournalEntry, Long> {
  @Query("SELECT je FROM JournalEntry je WHERE je.book.name = ?1")
  public List<JournalEntry> findByBookName(String bookName);

  @Transactional
  @Modifying
  @Query("DELETE FROM JournalEntry je WHERE je.book.name = ?1")
  public void deleteByBookName(String bookName);

  @Query("SELECT je FROM JournalEntry je WHERE je.client.firstName = ?1")
  public List<JournalEntry> findByClientFirstName(String firstName);

  @Transactional
  @Modifying
  @Query("DELETE FROM JournalEntry je WHERE je.client.firstName = ?1")
  public void deleteByClientFirstName(String firstName);
}
