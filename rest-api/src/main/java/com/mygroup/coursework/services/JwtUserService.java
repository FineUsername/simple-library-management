package com.mygroup.coursework.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.mygroup.coursework.entities.User;
import com.mygroup.coursework.exceptions.UserAlreadyExistsException;
import com.mygroup.coursework.repositories.UserRepository;
import com.mygroup.coursework.security.UserRole;

@Service
public class JwtUserService implements UserDetailsService {

  private final UserRepository repository;

  @Autowired
  public JwtUserService(UserRepository repository) {
    this.repository = repository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return repository.findByUsername(username).orElseThrow(
        () -> new UsernameNotFoundException(String.format("Username %s not found", username)));
  }

  public User save(User user) {
    if (!repository.findByUsername(user.getUsername()).isEmpty()) {
      throw new UserAlreadyExistsException(user.getUsername());
    }
    user.setAuthorities(List.of(UserRole.USER.getPrefixedName()));
    return repository.save(user);
  }

  public boolean usernameExists(String username) {
    return !repository.findByUsername(username).isEmpty();
  }

  public List<User> findAll() {
    return repository.findAll();
  }

  public void deleteById(long id) {
    repository.deleteById(id);
  }
}
