package com.mygroup.coursework.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mygroup.coursework.entities.Client;
import com.mygroup.coursework.repositories.ClientRepository;

@Service
public class ClientService {
  private final ClientRepository repository;

  @Autowired
  public ClientService(ClientRepository repository) {
    this.repository = repository;
  }

  public List<Client> findAll() {
    return repository.findAll();
  }

  public Client findById(long id) {
    return repository.findById(id).orElseThrow();
  }

  public List<Client> findByFullName(String firstName, String lastName) {
    return repository.findByFullName(firstName, lastName);
  }

  public Client save(Client client) {
    return repository.save(client);
  }

  public void deleteById(long id) {
    repository.deleteById(id);
  }

  public void deleteByFullName(String firstName, String lastName) {
    repository.deleteByFullName(firstName, lastName);
  }

  public void deleteAll() {
    repository.deleteAll();
  }
}
