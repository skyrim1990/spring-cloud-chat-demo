package com.tao.messageservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  /*
  @Bean
  public HeaderFilter headerFilter() {
    return new HeaderFilter();
  }
  */
 
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    http.authorizeRequests()
        .anyRequest().permitAll();

    /*
    http.authorizeRequests()
        .antMatchers("/ws/**").permitAll()
        .antMatchers("/test/**").permitAll()
        .antMatchers("/topic/**").authenticated()
        .anyRequest().authenticated();
    */


    // http.addFilterBefore(headerFilter(), UsernamePasswordAuthenticationFilter.class);
  }
   
}
