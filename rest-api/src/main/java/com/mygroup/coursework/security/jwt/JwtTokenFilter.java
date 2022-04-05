package com.mygroup.coursework.security.jwt;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import com.mygroup.coursework.services.JwtUserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

public class JwtTokenFilter extends OncePerRequestFilter {
  private final JwtProperties jwtProperties;
  private final JwtUserService jwtUserService;

  public JwtTokenFilter(JwtProperties jwtProperties, JwtUserService jwtUserService) {
    this.jwtProperties = jwtProperties;
    this.jwtUserService = jwtUserService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    String authorizationHeader = request.getHeader(jwtProperties.getAuthorizationHeader());
    if (!isValidAuthorizationHeader(authorizationHeader)) {
      filterChain.doFilter(request, response);
      return;
    }
    String token = authorizationHeader.replace(jwtProperties.getTokenPrefix(), "");
    try {
      Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(jwtProperties.getSecretKey()).build()
          .parseClaimsJws(token);
      Claims tokenBody = claims.getBody();
      if (!tokenBody.getExpiration().after(new Date())) {
        filterChain.doFilter(request, response);
        return;
      }
      SecurityContextHolder.getContext().setAuthentication(getAuthentication(tokenBody));
    } catch (JwtException e) {
      throw new IllegalStateException(String.format("Token %s cannot be trusted", token), e);
    }
    filterChain.doFilter(request, response);
  }

  private boolean isValidAuthorizationHeader(String authorizationHeader) {
    return (authorizationHeader != null) && !authorizationHeader.isEmpty()
        && authorizationHeader.startsWith(jwtProperties.getTokenPrefix());
  }

  private Authentication getAuthentication(Claims tokenBody) {
    Collection<? extends GrantedAuthority> authorities =
        jwtUserService.loadUserByUsername(tokenBody.getSubject()).getAuthorities();
    return new UsernamePasswordAuthenticationToken(tokenBody.getSubject(), null, authorities);
  }
}
