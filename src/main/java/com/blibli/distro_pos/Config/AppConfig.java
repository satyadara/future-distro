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
        driverManagerDataSource.setUrl("jdbc:postgresql://google/distro_pos?socketFactory=com.google.cloud.sql.postgres.SocketFactory&socketFactoryArg=future-distro-pos:asia-south1:distropos");
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
