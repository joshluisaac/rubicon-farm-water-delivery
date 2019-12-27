package com.rubiconwater.codingchallenge.joshluisaac.infrastructure.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable();
    http.authorizeRequests()
        .antMatchers("/", "/api/farmers/**", "/api/farmers", "/api/requests", "/api/requests/**")
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .httpBasic()
        .and()
        .logout()
        .permitAll();
  }
}
