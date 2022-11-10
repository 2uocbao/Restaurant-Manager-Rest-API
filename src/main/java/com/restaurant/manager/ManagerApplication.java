package com.restaurant.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class ManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManagerApplication.class, args);
	}

	 @SuppressWarnings("deprecation")
	@Bean
	   public WebMvcConfigurer corsConfigurer() {
			return new WebMvcConfigurerAdapter() {
	         @Override
	         public void addCorsMappings(CorsRegistry registry) {
	        	 String localhost = "http://localhost:3000";
	            registry.addMapping("/employee").allowedOrigins(localhost);
	            registry.addMapping("/branch").allowedOrigins(localhost);
	            registry.addMapping("/restaurant").allowedOrigins(localhost);
	            registry.addMapping("/table").allowedOrigins(localhost);	
	            registry.addMapping("/food").allowedOrigins(localhost);
	            registry.addMapping("/order").allowedOrigins(localhost);
	            registry.addMapping("/material").allowedOrigins(localhost);
	            registry.addMapping("/warehouse").allowedOrigins(localhost);
	         }
	      };
	   }
}
