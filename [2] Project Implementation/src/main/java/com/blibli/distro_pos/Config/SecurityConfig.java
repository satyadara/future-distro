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


    final DataSource dataSource;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public SecurityConfig(DataSource dataSource, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.dataSource = dataSource;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery(
                        "select username,password,enabled from users where username=?")
                .authoritiesByUsernameQuery(
                        "select username, role from user_roles where username=?")
                .passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
//                .antMatchers("").permitAll()
                .antMatchers("/admin/**", "/user/**", "/item/**",
                        "/discount/**", "/outcome/**", "/ledger/**", "/transaction/**").access("hasAuthority('MANAGER')")
                .antMatchers("/cashier/**").access("hasAnyAuthority('CASHIER', 'MANAGER')")
                .and()
                .formLogin().loginPage("/login")
                .usernameParameter("username").passwordParameter("password")
                .defaultSuccessUrl("/success")
                .and()
                .logout().logoutSuccessUrl("/?logout")
                .and()
                .exceptionHandling().accessDeniedPage("/403")
                .and()
                .csrf().disable();
    }
}
