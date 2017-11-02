package com.blibli.distro_pos.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;
import javax.xml.crypto.Data;

@Configuration
@EnableAutoConfiguration
public class SecurityConfig extends WebSecurityConfigurerAdapter {


   @Autowired
   DataSource dataSource;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery(
                        "select username,password,enabled from users where username=?")
                .authoritiesByUsernameQuery(
                        "select username, role from user_roles where username=?")
                .passwordEncoder(bCryptPasswordEncoder);
    }


//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//
//            http.authorizeRequests()
//                    .antMatchers("/", "/home").permitAll()
//                    .antMatchers("/admin", "/view_user","/add_user").access("hasAuthority('ROLE_ADMIN')")
//                    .anyRequest().authenticated()
//                    .and()
//                        .formLogin().loginPage("/login").permitAll()
//                    .and()
//                        .logout().permitAll()
//                    .and()
//                        .exceptionHandling().accessDeniedPage("/403")
//                    .and()
//                        .csrf();
//
//
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/add_user","/view_user").permitAll()
                .antMatchers("/admin").access("hasRole('ROLE_ADMIN')")
                .and()
                .formLogin().loginPage("/login")
                .usernameParameter("username").passwordParameter("password")
                .and()
                .logout().logoutSuccessUrl("/login?logout")
                .and()
                .exceptionHandling().accessDeniedPage("/403")
                .and()
                .csrf().disable();
    }
}
