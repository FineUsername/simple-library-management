package com.mygroup.coursework.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import com.mygroup.coursework.security.jwt.JwtProperties;
import com.mygroup.coursework.security.jwt.JwtTokenFilter;
import com.mygroup.coursework.services.JwtUserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final JwtUserService userService;
  private final JwtProperties jwtProperties;

  @Autowired
  public SecurityConfig(JwtUserService userService, JwtProperties jwtProperties) {
    this.userService = userService;
    this.jwtProperties = jwtProperties;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable().httpBasic().disable().formLogin().disable().sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
        .addFilterAfter(new JwtTokenFilter(jwtProperties, userService),
            BasicAuthenticationFilter.class)
        .authorizeRequests().antMatchers("/users/sign*").permitAll().antMatchers(HttpMethod.GET)
        .hasAnyRole(UserRole.USER.name(), UserRole.ADMIN.name()).antMatchers("**")
        .hasRole(UserRole.ADMIN.name()).and().exceptionHandling()
        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(daoAuthenticationProvider());
  }

  @Bean
  public DaoAuthenticationProvider daoAuthenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setPasswordEncoder(passwordEncoder());
    provider.setUserDetailsService(userService);
    return provider;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(jwtProperties.getPasswordEncoderStrength());
  }
}
