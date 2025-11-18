package com.mlsci.lights.status;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.mlsci.lights.action.ActionSchedule;
import com.mlsci.lights.client.Color;
import com.mlsci.lights.client.LightClient;
import com.mlsci.lights.repo.LightRepo;
import com.mlsci.lights.repo.Room;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
class StatusController {
	private final LightRepo lightRepo;
	private final ActionSchedule actionSchedule;
	private final LightClient lightClient;
	
	@GetMapping("/status")
	String status(ModelMap map) {
		
		map.put("chase", lightRepo.getChase(Room.MAIN));
		map.put("lights", lightRepo.getAll());
		return "status";
	}
	
	@GetMapping("/")
	String home(ModelMap map) {
		return "home";
	}
	
	@GetMapping("/allwarmwhite")
	String allwarmwhite(ModelMap map) {
		actionSchedule.pause();
		for(var light : lightRepo.getAll()) {
			lightClient.setWhite(light, "1", 200, 330);
		}
		map.put("lights", lightRepo.getAll());
		map.put("oneColor", "Warm White");
		return "allcolor";
	}
	
	
	String all(Color color, String name, ModelMap map) {
		for(var light : lightRepo.getAll()) {
			lightClient.setColor(light,color, "1", 220);
		}
		map.put("lights", lightRepo.getAll());
		map.put("oneColor", name);
		return "allcolor";
	}
	
	@GetMapping("/allgreen")
	String allgreen(ModelMap map) { return all(Color.GREEN, "Green", map); }
	
	@GetMapping("/allred")
	String allred(ModelMap map) { return all(Color.RED, "Red", map); }
	
	@GetMapping("/allpurple")
	String allpurple(ModelMap map) { return all(Color.PURPLE, "Purple", map); }
	
	
	@GetMapping("/resume")
	String resume(ModelMap map) {
		actionSchedule.resume();
	
		map.put("lights", lightRepo.getAll());
		return "resume";
	}
}
