package com.mygroup.coursework.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

@Entity
@Table(name = "Journal")
public class JournalEntry {

  @Id
  @JsonProperty("id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", columnDefinition = "INT")
  private long id;

  @JsonProperty("book_id")
  @ManyToOne(optional = false)
  @JoinColumn(foreignKey = @ForeignKey(name = "fk_journal_books"), name = "book_id",
      columnDefinition = "INT")
  private Book book;

  @JsonProperty("client_id")
  @ManyToOne(optional = false)
  @JoinColumn(foreignKey = @ForeignKey(name = "fk_journal_clients"), name = "client_id",
      columnDefinition = "INT")
  private Client client;

  @JsonProperty("date_beg")
  @Column(name = "date_beg", columnDefinition = "TIMESTAMP(3)")
  private Timestamp dateBeg;

  @JsonProperty("date_end")
  @Column(name = "date_end", columnDefinition = "TIMESTAMP(3)")
  private Timestamp dateEnd;

  @JsonProperty("date_ret")
  @Column(name = "date_ret", columnDefinition = "TIMESTAMP(3)")
  private Timestamp dateRet;

  public JournalEntry() {}

  public JournalEntry(Book book, Client client, Timestamp dateBeg, Timestamp dateEnd,
      Timestamp dateRet) {
    this.book = book;
    this.client = client;
    this.dateBeg = dateBeg;
    this.dateEnd = dateEnd;
    this.dateRet = dateRet;
  }

  public JournalEntry(long id, Book book, Client client, Timestamp dateBeg, Timestamp dateEnd,
      Timestamp dateRet) {
    this.id = id;
    this.book = book;
    this.client = client;
    this.dateBeg = dateBeg;
    this.dateEnd = dateEnd;
    this.dateRet = dateRet;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Book getBook() {
    return book;
  }

  public void setBook(Book book) {
    this.book = book;
  }

  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }

  public Timestamp getDateBeg() {
    return dateBeg;
  }

  public void setDateBeg(Timestamp dateBeg) {
    this.dateBeg = dateBeg;
  }

  public Timestamp getDateEnd() {
    return dateEnd;
  }

  public void setDateEnd(Timestamp dateEnd) {
    this.dateEnd = dateEnd;
  }

  public Timestamp getDateRet() {
    return dateRet;
  }

  public void setDateRet(Timestamp dateRet) {
    this.dateRet = dateRet;
  }

  @JsonGetter("book_id")
  public long getBookId() {
    return book.getId();
  }

  @JsonSetter("book_id")
  public void setBookId(long id) {
    if (book == null) {
      book = new Book();
      book.setId(id);
    }
  }

  @JsonGetter("client_id")
  public long getClientId() {
    return client.getId();
  }

  @JsonSetter("client_id")
  public void setClientId(long id) {
    if (client == null) {
      client = new Client();
      client.setId(id);
    }
  }

  @Override
  public String toString() {
    return "JournalEntry [id=" + id + ", book=" + book.getName() + ", client="
        + client.getFirstName() + " " + client.getLastName() + ", dateBeg=" + dateBeg + ", dateEnd="
        + dateEnd + ", dateRet=" + dateRet + "]";
  }

}
