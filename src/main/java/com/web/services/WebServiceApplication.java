package com.web.services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@EnableSwagger2
@SpringBootApplication
@EnableScheduling
public class WebServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebServiceApplication.class, args);
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:8080");
            }
        };
    }

    @Bean
    public Docket swaggerConfiguration() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .select()
                .paths(PathSelectors.ant("/api/**"))
                .apis(RequestHandlerSelectors.basePackage("com.web.services"))
                .build()
                .apiInfo(apiDetails());
    }

    public ApiInfo apiDetails() {
        String introduction =
                "Hey there, my name is Jashua, nice to meet you!\n\n" +

                        "I'm a backend developer with over a year of experience, I mainly use Java and the Spring framework,\n" +
                        "but I can work with anything, I've used PHP, JavaScript and Python among others.\n\n" +

                        "Initially, I made this project with the intention to be used as a base for my personal projects,\n" +
                        "it quickly turned into open source, I hope it can be used by other developers as a reference.\n\n" +

                        "1) AOP and Logback to keep track of service and endpoint responses through logging.\n" +
                        "2) Follows the SOLID principles to accomplish clean, extendable and maintainable code.\n" +
                        "3) Its architecture is based on the Service and DAO patterns.\n" +
                        "4) Object relational mapping and user input validation is done through Hibernate.\n" +
                        "5) Role based authorizations with Spring Security and JWT, IP banning to avoid brute force.\n\n";

        return new ApiInfo(
                "Spring Boot Backend API | Reference",
                "Source code: https://github.com/Jouo/solid-spring-boot\n\n" +
                        introduction,
                "open source",
                "https://opensource.org/ToS",
                new springfox.documentation.service.Contact(
                        "Abel Jashua Heredia Chávez",
                        "https://github.com/Jouo",
                        "Jashua.Heredia@hotmail.com"),
                "GNU General Public License v3.0",
                "https://www.gnu.org/licenses/gpl-3.0.en.html",
                Collections.emptyList()
        );
    }
}
