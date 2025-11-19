package com.mlsci.lights.status;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.mlsci.lights.action.ActionSchedule;
import com.mlsci.lights.client.Color;
import com.mlsci.lights.client.LightClient;
import com.mlsci.lights.repo.Bulb;
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
		map.put("colorOptions",ColorOption.values());
		map.put("brightOptions", BrightOption.values());
		return "home";
	}
	
	@GetMapping("/allwarmwhite")
	String allwarmwhite(ModelMap map) {
		actionSchedule.pause();
		for(var light : lightRepo.getAll()) {
			lightClient.setWhite(light, "5", 250, 330);
		}
		map.put("lights", lightRepo.getAll());
		map.put("oneColor", "Warm White");
		return "allcolor";
	}
	
	@GetMapping("/all/{colorOption}/{brightOption}")
	String all(ModelMap map, @PathVariable ColorOption colorOption,
			@PathVariable BrightOption brightOption) {
		actionSchedule.pause();
		for(var light : lightRepo.getAll()) {
			lightClient.setColor(light,colorOption.getColor(), 
					"5", brightOption.getBrightness());
		}
		
		map.put("oneColor", colorOption.getLabel()  +  " " + brightOption.getLabel());
		return "allColor";
	}
	
	@GetMapping("/alloff")
	String allwarmoff(ModelMap map) {
		actionSchedule.pause();
		for(var light : lightRepo.getAll()) {
			lightClient.setOff(light, "5");
		}
		map.put("lights", lightRepo.getAll());
		map.put("oneColor", "OFF");
		return "allcolor";
	}
	
	
	
	String all(Color color, String name, ModelMap map) {
		actionSchedule.pause();
		for(var light : lightRepo.getAll()) {
			lightClient.setColor(light,color, "5", 250);
		}
		map.put("lights", lightRepo.getAll());
		map.put("oneColor", name);
		return "allcolor";
	}
	
	@GetMapping("/allgreen")
	String allgreen(ModelMap map) { return all(Color.GREEN, "Green", map); }
	
	@GetMapping("/allblue")
	String allblue(ModelMap map) { return all(Color.BLUE, "Blue", map); }
	
	
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
	
	
	@GetMapping("/mapping") 
	String mapping(ModelMap map) {
		var maxRow = 0;
		var maxCol = 0;
		for(var b : Bulb.values()) {
			maxRow = Math.max(maxRow, b.getRow());
			maxCol = Math.max(maxCol, b.getCol());
		}
		var grid = new Grid(maxRow, maxCol);
		for(var bulb : Bulb.values()) { grid.add(bulb, lightRepo.get(bulb)); }
		map.put("grid", grid);
		return "mapping";
	}
	
}
