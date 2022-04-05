package com.mygroup.coursework.security.jwt;

import javax.crypto.SecretKey;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.security.Keys;

@Component
@ConfigurationProperties(prefix = "application.jwt")
public class JwtProperties {

  private long expirationTimeInMs;
  private String jwtKey;
  private String tokenPrefix;
  private int passwordEncoderStrength;

  public int getPasswordEncoderStrength() {
    return passwordEncoderStrength;
  }

  public void setPasswordEncoderStrength(int passwordEncoderStrength) {
    this.passwordEncoderStrength = passwordEncoderStrength;
  }

  public String getTokenPrefix() {
    return tokenPrefix;
  }

  public void setTokenPrefix(String tokenPrefix) {
    this.tokenPrefix = tokenPrefix;
  }

  public long getExpirationTimeInMs() {
    return expirationTimeInMs;
  }

  public void setExpirationTimeInMs(long expirationTime) {
    this.expirationTimeInMs = expirationTime;
  }

  public String getJwtKey() {
    return jwtKey;
  }

  public void setJwtKey(String jwtKey) {
    this.jwtKey = jwtKey;
  }

  public SecretKey getSecretKey() {
    return Keys.hmacShaKeyFor(jwtKey.getBytes());
  }

  public String getAuthorizationHeader() {
    return HttpHeaders.AUTHORIZATION;
  }

}
