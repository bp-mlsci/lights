package com.mlsci.lights.status;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.mlsci.lights.action.ActionSchedule;
import com.mlsci.lights.client.Color;
import com.mlsci.lights.client.LightClient;
import com.mlsci.lights.repo.Bulb;
import com.mlsci.lights.repo.LightMode;
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
		
		map.put("chase", lightRepo.getChase(Room.MAIN, LightMode.AUTO));
		map.put("lights", lightRepo.getAll());
		return "status";
	}
	
	@GetMapping("/")
	String home(ModelMap map) {
		map.put("colorOptions",ColorOption.values());
		map.put("brightOptions", BrightOption.values());
		map.put("rooms", Room.values());
		return "home";
	}
	
	@GetMapping("/allwarmwhite")
	String allwarmwhite(ModelMap map) {
		
		for(var light : lightRepo.getAll()) {
			if(lightClient.setWhite(light, "5", 250, 330)) {
				light.setLightMode(LightMode.MANUAL);
			}
		}
		map.put("lights", lightRepo.getAll());
		map.put("oneColor", "Warm White");
		return "allcolor";
	}
	
	@GetMapping("/all/{colorOption}/{brightOption}")
	String all(ModelMap map, @PathVariable ColorOption colorOption,
			@PathVariable BrightOption brightOption) {
		
		for(var light : lightRepo.getAll()) {
			if(lightClient.setColor(light,colorOption.getColor(), 
					"5", brightOption.getBrightness())) {
				light.setLightMode(LightMode.MANUAL);
			}
		}
		
		map.put("oneColor", colorOption.getLabel()  +  " " + brightOption.getLabel());
		return "allColor";
	}
	
	@GetMapping("/room/{room}")
	String room(ModelMap map, @PathVariable Room room) {
		map.put("room", room);
		map.put("colorOptions",ColorOption.values());
		map.put("brightOptions", BrightOption.values());
		map.put("rooms", Room.values());
		return "room";
	}
	
	
	@GetMapping("/light/{bulb}")
	String light(ModelMap map, @PathVariable Bulb bulb) {

		map.put("colorOptions",ColorOption.values());
		map.put("brightOptions", BrightOption.values());
		map.put("light", lightRepo.get(bulb));
		return "light";
	}
	
	
	
	@GetMapping("/roomoff/{room}")
	String roomoff(ModelMap map, @PathVariable Room room) {
		for(var light : lightRepo.getAll(room)) {
			if( lightClient.setOff(light, "5") ) {
				light.setLightMode(LightMode.MANUAL);
			}
		}
		map.put("oneColor", room.getLabel() + " Off");
		return "allColor";
	}
	
	
	@GetMapping("/lightoff/{bulb}")
	String lightoff(ModelMap map, @PathVariable Bulb bulb) {
		var light = lightRepo.get(bulb);
		if( lightClient.setOff(light, "5") ) {
			light.setLightMode(LightMode.MANUAL);
		}
		map.put("oneColor", bulb.getDescription() + " Off");
		return "allColor";
	}
	
	@GetMapping("/roomresume/{room}")
	String roomresume(ModelMap map, @PathVariable Room room) {
		for(var light : lightRepo.getAll(room)) {
			light.setLightMode(LightMode.AUTO);
		}
		map.put("oneColor", room.getLabel() + " Resumed Automatic");
		return "allColor";
	}

	
	@GetMapping("/lightresume/{bulb}")
	String lightresume(ModelMap map, @PathVariable Bulb bulb) {
		var light = lightRepo.get(bulb);
		light.setLightMode(LightMode.AUTO);
		
		map.put("oneColor", bulb.getDescription() + " Resumed Automatic");
		return "allColor";
	}

	
	
	@GetMapping("/roomall/{room}/{colorOption}/{brightOption}")
	String roomAll(ModelMap map, @PathVariable Room room,
			@PathVariable ColorOption colorOption,
			@PathVariable BrightOption brightOption) {
		map.put("room", room);
		for(var light : lightRepo.getAll(room)) {
			if(lightClient.setColor(light,colorOption.getColor(), 
					"5", brightOption.getBrightness())) {
				light.setLightMode(LightMode.MANUAL);
			}
		}
		map.put("oneColor", 
				"Room "+ room.name() + " " +
				colorOption.getLabel()  +  " " + brightOption.getLabel());

		return "allcolor";
	}
	
	@GetMapping("/lighton/{bulb}/{colorOption}/{brightOption}")
	String lighton(ModelMap map, @PathVariable Bulb bulb,
			
			@PathVariable ColorOption colorOption,
			@PathVariable BrightOption brightOption) {

		var light = lightRepo.get(bulb);
		if(lightClient.setColor(light,colorOption.getColor(), 
				"5", brightOption.getBrightness())) {
			light.setLightMode(LightMode.MANUAL);
		}
		map.put("oneColor", 
				"Light "+ light.getBulb().name() + " " +
				colorOption.getLabel()  +  " " + brightOption.getLabel());

		return "allcolor";
	}
	
	@GetMapping("/alloff")
	String allwarmoff(ModelMap map) {
		for(var light : lightRepo.getAll()) {
			if( lightClient.setOff(light, "5") ) {
				light.setLightMode(LightMode.MANUAL);
			}
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
		for(var light: lightRepo.getAll()) {
			light.setLightMode(LightMode.AUTO);
		}
	
		map.put("lights", lightRepo.getAll());
		return "resume";
	}
	
	
	@GetMapping("/mapping") 
	String mapping(ModelMap map) {
		map.put("grid", lightRepo.getGrid());
		return "mapping";
	}
	
}
