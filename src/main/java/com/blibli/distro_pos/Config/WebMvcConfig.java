package com.blibli.distro_pos.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

@Configuration
@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/img/**").addResourceLocations("classpth:/img/");
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
//        registry.addResourceHandler("/static/js/**").addResourceLocations("static/js/");
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        return bCryptPasswordEncoder;
    }

    @Bean
    public TemplateResolver templateResolver(){
        TemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML5");
        return templateResolver;
    }
    @Bean
    public SpringTemplateEngine templateEngine() {

        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());

        return templateEngine;
    }

    @Bean
    public ViewResolver viewResolver() {

        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();

        viewResolver.setTemplateEngine(templateEngine());
        viewResolver.setCharacterEncoding("UTF-8");

        return viewResolver;
    }

    @Bean
    public MultipartResolver multipartResolver() {

        return new StandardServletMultipartResolver();
    }


}
