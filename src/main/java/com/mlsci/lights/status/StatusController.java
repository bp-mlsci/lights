package com.mlsci.lights.status;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.mlsci.lights.repo.LightRepo;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
class StatusController {
	private final LightRepo lightRepo;
	
	@GetMapping("/status")
	String status(ModelMap map) {
		
		map.put("lights", lightRepo.getChase());
		return "status";
		
		
		
	}
}
