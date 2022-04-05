package com.mygroup.coursework;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import com.mygroup.coursework.entities.User;
import com.mygroup.coursework.exceptions.UserAlreadyExistsException;
import com.mygroup.coursework.security.UserRole;
import com.mygroup.coursework.services.JwtUserService;

@Component
@ConfigurationProperties(prefix = "application.admin")
public class AdminInit implements CommandLineRunner {

  @Autowired
  private JwtUserService userService;
  private String username;
  private String password;

  @Override
  public void run(String... args) {
    User admin = new User(username, password, List.of(UserRole.ADMIN.getPrefixedName()));
    try {
      userService.save(admin);
    } catch (UserAlreadyExistsException e) {
    }
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setPassword(String password) {
    this.password = password;
  }


}
