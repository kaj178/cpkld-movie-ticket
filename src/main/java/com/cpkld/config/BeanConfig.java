package com.cpkld.config;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public DataSource getDataSource() {
        return DataSourceBuilder.create()
          .driverClassName("org.postgresql.Driver")
          .url("jdbc:postgresql://movie-database.cjmz5u8uda2b.ap-southeast-1.rds.amazonaws.com:5432/initual_db")
          .username("postgres")
          .password("admin123")
          .build();	
    }

}
