package com.todoproject.demo.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceDebugConfig {

    @Bean
    CommandLineRunner printJdbcUrl(HikariDataSource ds) {
        return args -> {
            System.out.println(">>> JDBC URL: " + ds.getJdbcUrl());
        };
    }
}