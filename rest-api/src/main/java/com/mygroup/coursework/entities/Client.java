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
@Table(name = "Clients")
public class Client {

  @Id
  @JsonProperty("id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", columnDefinition = "INT")
  private long id;

  @JsonProperty("first_name")
  @Column(name = "first_name", columnDefinition = "VARCHAR", length = 20)
  private String firstName;

  @JsonProperty("last_name")
  @Column(name = "last_name", columnDefinition = "VARCHAR", length = 20)
  private String lastName;

  @JsonProperty("partner_name")
  @Column(name = "partner_name", columnDefinition = "VARCHAR", length = 20)
  private String partnerName;

  @JsonProperty("passport_seria")
  @Column(name = "passport_seria", columnDefinition = "VARCHAR", length = 20)
  private String passportSeria;

  @JsonProperty("passport_num")
  @Column(name = "passport_num", columnDefinition = "VARCHAR", length = 20)
  private String passportNum;

  @JsonIgnore
  @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
  private Collection<JournalEntry> entries;

  public Client() {}

  public Client(String firstName, String lastName, String partnerName, String passportSeria,
      String passportNum, Collection<JournalEntry> entries) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.partnerName = partnerName;
    this.passportSeria = passportSeria;
    this.passportNum = passportNum;
    this.entries = entries;
  }

  public Client(long id, String firstName, String lastName, String partnerName,
      String passportSeria, String passportNum, Collection<JournalEntry> entries) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.partnerName = partnerName;
    this.passportSeria = passportSeria;
    this.passportNum = passportNum;
    this.entries = entries;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getPartnerName() {
    return partnerName;
  }

  public void setPartnerName(String partnerName) {
    this.partnerName = partnerName;
  }

  public String getPassportSeria() {
    return passportSeria;
  }

  public void setPassportSeria(String passportSeria) {
    this.passportSeria = passportSeria;
  }

  public String getPassportNum() {
    return passportNum;
  }

  public void setPassportNum(String passportNum) {
    this.passportNum = passportNum;
  }

  public Collection<JournalEntry> getEntries() {
    return entries;
  }

  public void setEntries(Collection<JournalEntry> entries) {
    this.entries = entries;
  }

  @Override
  public String toString() {
    return "Client [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName
        + ", partnerName=" + partnerName + ", passportSeria=" + passportSeria + ", passportNum="
        + passportNum + "]";
  }

}
