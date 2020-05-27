package com.dxc.graphql;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
@EnableJpaAuditing
@Configuration
@ComponentScan(basePackages = "com.dxc.graphql")
public class GraphqlApplication {

	public static void main(String[] args) throws IOException {	
		
		SpringApplication.run(GraphqlApplication.class, args);
	}

}
