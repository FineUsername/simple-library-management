package com.mygroup.coursework.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mygroup.coursework.entities.BookType;

@Repository
public interface BookTypeRepository extends JpaRepository<BookType, Long> {
  @Query("SELECT b FROM BookType b WHERE b.name = ?1")
  public List<BookType> findByName(String name);

  @Transactional
  @Modifying
  @Query("DELETE FROM BookType b WHERE b.name = ?1")
  public void deleteByName(String name);
}
