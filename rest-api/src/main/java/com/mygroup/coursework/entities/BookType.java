package com.mygroup.coursework.entities;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "Book_types")
public class BookType {

  @Id
  @JsonProperty("id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", columnDefinition = "INT")
  private long id;

  @JsonProperty("name")
  @Column(name = "name", columnDefinition = "VARCHAR", length = 50)
  private String name;

  @JsonProperty("count")
  @Column(name = "cnt", columnDefinition = "INT")
  private int cnt;

  @JsonProperty("fine")
  @Column(name = "fine", columnDefinition = "DECIMAL", scale = 18, precision = 2)
  private long fine;

  @JsonProperty("day_count")
  @Column(name = "day_count", columnDefinition = "INT")
  private int dayCount;

  @JsonIgnore
  @OneToMany(mappedBy = "type", cascade = CascadeType.ALL)
  private Collection<Book> books;

  public BookType() {}

  public BookType(String name, int cnt, long fine, int dayCount, Collection<Book> books) {
    this.name = name;
    this.cnt = cnt;
    this.fine = fine;
    this.dayCount = dayCount;
    this.books = books;
  }

  public BookType(long id, String name, int cnt, long fine, int dayCount, Collection<Book> books) {
    this.id = id;
    this.name = name;
    this.cnt = cnt;
    this.fine = fine;
    this.dayCount = dayCount;
    this.books = books;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getCnt() {
    return cnt;
  }

  public void setCnt(int cnt) {
    this.cnt = cnt;
  }

  public long getFine() {
    return fine;
  }

  public void setFine(long fine) {
    this.fine = fine;
  }

  public int getDayCount() {
    return dayCount;
  }

  public void setDayCount(int dayCount) {
    this.dayCount = dayCount;
  }

  public Collection<Book> getBooks() {
    return books;
  }

  public void setBooks(Collection<Book> books) {
    this.books = books;
  }

  @Override
  public String toString() {
    return "BookType [id=" + id + ", name=" + name + ", cnt=" + cnt + ", fine=" + fine
        + ", dayCount=" + dayCount + "]";
  }

}
