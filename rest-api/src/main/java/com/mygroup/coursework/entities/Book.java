package com.mygroup.coursework.entities;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

@Entity
@Table(name = "Books")
public class Book {

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

  @JsonProperty("type_id")
  @ManyToOne(optional = false)
  @JoinColumn(foreignKey = @ForeignKey(name = "fk_books_book_types"), name = "type_id",
      columnDefinition = "INT", referencedColumnName = "id")
  private BookType type;

  @JsonIgnore
  @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
  private Collection<JournalEntry> entries;

  public Book() {}

  public Book(String name, int cnt, BookType type, Collection<JournalEntry> entries) {
    this.name = name;
    this.cnt = cnt;
    this.type = type;
    this.entries = entries;
  }

  public Book(long id, String name, int cnt, BookType type, Collection<JournalEntry> entries) {
    this.id = id;
    this.name = name;
    this.cnt = cnt;
    this.type = type;
    this.entries = entries;
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

  public BookType getType() {
    return type;
  }

  public void setType(BookType type) {
    this.type = type;
  }

  @JsonGetter("type_id")
  public long getTypeId() {
    return type.getId();
  }

  @JsonSetter("type_id")
  public void setTypeId(long id) {
    if (type == null) {
      type = new BookType();
      type.setId(id);
    }
  }

  public Collection<JournalEntry> getEntries() {
    return entries;
  }

  public void setEntries(Collection<JournalEntry> entries) {
    this.entries = entries;
  }

  @Override
  public String toString() {
    return "Book [id=" + id + ", name=" + name + ", cnt=" + cnt + ", type=" + type.getName() + "]";
  }
}
