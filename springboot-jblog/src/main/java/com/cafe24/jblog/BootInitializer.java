package com.cafe24.jblog;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class BootInitializer extends SpringBootServletInitializer { //tomcat이 여길 실행한다 main은 실행하지 못하므로  

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(BootApp.class);
	}
}
