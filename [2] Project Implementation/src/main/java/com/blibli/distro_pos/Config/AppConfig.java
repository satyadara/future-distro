package com.blibli.distro_pos.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@Configuration
@ComponentScan({"com.blibli.distro_pos.*"})
@Import({SecurityConfig.class})
public class AppConfig {

    //database mana yang bakal dipakai
    @Bean(name = "dataSource")
    public DriverManagerDataSource dataSource() {

        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("org.postgresql.Driver");
        driverManagerDataSource.setUrl("jdbc:postgresql://35.200.231.200:5432/distro_pos");
        driverManagerDataSource.setUsername("postgres");
        driverManagerDataSource.setPassword("postgres");

        return driverManagerDataSource;
    }

    //Lokasi front-end
//    @Bean
//    public InternalResourceViewResolver viewResolver() {
//
//        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
//        viewResolver.setViewClass(JstlView.class);
//        viewResolver.setPrefix("/WEB-INF/jsp/");
//        viewResolver.setSuffix(".jsp");
//
//        return viewResolver;
//    }
}
