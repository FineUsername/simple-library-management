package com.mygroup.coursework.security.jwt;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;

@Component
public class JwtTokenProvider {

  private final JwtProperties jwtProperties;

  @Autowired
  public JwtTokenProvider(JwtProperties jwtProperties) {
    this.jwtProperties = jwtProperties;
  }

  public String createToken(String username) {
    Date currentDate = new Date();
    return Jwts.builder().setSubject(username).setIssuedAt(currentDate)
        .setExpiration(new Date(currentDate.getTime() + jwtProperties.getExpirationTimeInMs()))
        .signWith(jwtProperties.getSecretKey()).compact();
  }
}
