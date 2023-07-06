package com.restaurant.manager.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.restaurant.manager.jwt.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@ComponentScan("com.restaurant.")
public class WebSecurityConfig {
	
	private static final String ADMIN = "ADMIN";
	private static final String EMPLOYEE = "EMPLOYEE";
	private static final HttpMethod PUT = HttpMethod.PUT;
	private static final HttpMethod GET = HttpMethod.GET;
	
	private final JwtAuthenticationFilter authenticationFilter;

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeHttpRequests()
			.antMatchers("/account/**").permitAll()
			
			.antMatchers(GET, "/branch/**").permitAll()
			.antMatchers("/branch/**").hasRole(ADMIN)
			
			.antMatchers(GET, "/employee/detail**").hasRole(EMPLOYEE)
			.antMatchers(PUT, "/employee/update**").hasRole(EMPLOYEE)
			.antMatchers("/employee/**").hasRole(ADMIN)
			
			.antMatchers("/food/**").hasRole(ADMIN)
			
			.antMatchers("/material/**").hasRole(ADMIN)
			
			.antMatchers("/order/**").hasRole(ADMIN)
			.antMatchers("/order/**").hasRole(EMPLOYEE)
			
			.antMatchers("/report/**").hasRole(ADMIN)
			
			.antMatchers(GET, "/restaurant/detail/**").permitAll()
			.antMatchers("/restaurant/**").hasRole(ADMIN)
			
			.antMatchers(GET, "/table/**").permitAll()
			.antMatchers("/table/**").hasRole(ADMIN)
			
			.antMatchers("/warehouse/**").hasRole(ADMIN)
			
			.anyRequest().authenticated();
		
		http.logout()
			.invalidateHttpSession(true).clearAuthentication(true);
		
		http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
}
