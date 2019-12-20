package com.rubiconwater.codingchallenge.joshluisaac.infrastructure.security;

import com.rubiconwater.codingchallenge.joshluisaac.businessactivities.Routes;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable();
    // http.authorizeRequests().antMatchers("/",
    // Routes.Farmers.FARMERS_ALL).permitAll().anyRequest().authenticated();

    http.authorizeRequests()
        .antMatchers("/", Routes.Farmers.FARMERS_ALL)
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .httpBasic()
        // .and()
        // .formLogin()
        // .loginPage("/login")
        // .permitAll()
        .and()
        .logout()
        .permitAll();
  }

  /*    @Override
  public void configure(WebSecurity web) {
      super.configure(web);
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
      super.configure(auth);
  }*/
}
