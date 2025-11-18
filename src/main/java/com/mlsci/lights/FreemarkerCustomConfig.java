package com.mlsci.lights;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FreemarkerCustomConfig {
	private final FreeMarkerConfigurer freeMarkerConfigurer;
	
	@PostConstruct
	void init() {
		var config = freeMarkerConfigurer.getConfiguration();
		config.addAutoImport("m", "macros.ftlh");
		try {
			config.setSharedVariable("myname", "value");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
