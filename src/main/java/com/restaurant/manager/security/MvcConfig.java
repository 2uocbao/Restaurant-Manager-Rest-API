package com.restaurant.manager.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SuppressWarnings("deprecation")
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/home").setViewName("trang_chu");
		registry.addViewController("/").setViewName("trang_chu");
		registry.addViewController("/login").setViewName("dang_nhap");
		registry.addViewController("/hello").setViewName("khu_vuc_bao_mat");
		registry.addViewController("/admin").setViewName("admin");
		registry.addViewController("/403").setViewName("403");
	}

}
