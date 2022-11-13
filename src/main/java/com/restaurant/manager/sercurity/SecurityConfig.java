//package com.restaurant.manager.sercurity;
//
//import java.text.Format;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
//import com.restaurant.manager.repository.EmployeeRepository;
//
//@SuppressWarnings("deprecation")
//@EnableWebSecurity
//@Configuration
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//	@Autowired
//	EmployeeRepository employRepo;
//
//	public SecurityConfig(EmployeeRepository employRepo) {
//		this.employRepo = employRepo;
//	}
//
//	@Override
//	public void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(username -> employRepo.findByPhone(username)
//				.orElseThrow(() -> new UsernameNotFoundException("usernotfound")));
//	}
//}
