package com.example.spring_task.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    //konfiguracja zabezpieczeń dla protokołu http
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/addpost").hasAnyAuthority("role_user", "role_admin")
                .antMatchers("/update/*").hasAnyAuthority("role_user", "role_admin")
                .antMatchers("/delete/*").hasAnyAuthority("role_user", "role_admin")
                .antMatchers("/admin").hasAnyAuthority("role_admin")
                .anyRequest().permitAll()
        .and().csrf().disable()
        .formLogin().loginPage("/login")
                .usernameParameter("login")
                .passwordParameter("password")
                .loginProcessingUrl("/login-process")
                .failureUrl("/errorLogin")
                .defaultSuccessUrl("/")
        .and().logout().logoutUrl("/logout").logoutSuccessUrl("/");
    }

    @Autowired
    DataSource dataSource;

    @Autowired
    PasswordEncoder passwordEncoder;

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .usersByUsernameQuery("SELECT u.email, u.password, u.activity_flag FROM user u WHERE u.email = ?")
                .authoritiesByUsernameQuery("SELECT u.email, r.role_name FROM " +
                                            "user u JOIN user_role ur ON (u.id = ur.user_id) " +
                                            "JOIN role r ON (r.id = ur.role_id) " +
                                            "WHERE u.email = ?")
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder);
    }

}
