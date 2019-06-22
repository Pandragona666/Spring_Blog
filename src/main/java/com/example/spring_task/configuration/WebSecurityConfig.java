package com.example.spring_task.configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.stereotype.Component;

@Component
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    //konfiguracja zabezpieczeń dla protokołu http
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/addpost").hasAnyRole("USER")
                .antMatchers("/update/*").hasAnyRole("USER")
                .anyRequest().permitAll()
        .and().formLogin()
        .and().httpBasic();

    }

}
