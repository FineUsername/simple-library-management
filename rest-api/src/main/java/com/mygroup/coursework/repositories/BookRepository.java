package com.mygroup.coursework.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mygroup.coursework.entities.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

  @Query("SELECT b FROM Book b WHERE b.name = ?1")
  public List<Book> findByName(String name);

  @Transactional
  @Modifying
  @Query("DELETE FROM Book b WHERE b.name = ?1")
  public void deleteByName(String name);
}
