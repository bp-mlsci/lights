package com.mlsci.lights.status;

import java.util.Collection;
import java.util.function.Predicate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.mlsci.lights.NiceConcurrent;
import com.mlsci.lights.client.Color;
import com.mlsci.lights.client.LightClient;
import com.mlsci.lights.repo.Bulb;
import com.mlsci.lights.repo.Light;
import com.mlsci.lights.repo.LightMode;
import com.mlsci.lights.repo.LightRepo;
import com.mlsci.lights.repo.Room;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;



@Controller
@Slf4j
@RequiredArgsConstructor
class StatusController {
	private final LightRepo lightRepo;

	private final LightClient lightClient;
	

	public static final String RESULT = "result";
	public static final String BACK = "back";
	public static final String HOME = "/";
	
	
	
	void data(ModelMap map) {
		map.put("colorOptions",ColorOption.values());
		map.put("brightOptions", BrightOption.values());
		map.put("rooms", Room.values());
	}
	
	
	@GetMapping("/status")
	String status(ModelMap map) {
		data(map);
		map.put("chase", lightRepo.getChase(Room.MAIN, LightMode.AUTO));
		map.put("lights", lightRepo.getAll());
		return "status";
	}
	
	
	
	@GetMapping(HOME)
	String home(ModelMap map) {
		data(map);
		return "home";
	}
	
	@GetMapping("/allwarmwhite")
	String allwarmwhite(ModelMap map) {
		data(map);
		launch(lightRepo.getAll(), l -> lightClient.setWhite(l, "5", 250, 330));
		return resultHome(map,"All Lights Set To Warm White");
	}
	
	
	
	String result(ModelMap map, String res, String back) {
		map.put(RESULT, res);
		map.put(BACK, back);
		return RESULT;
	}
	
	String resultHome(ModelMap map, String res) {
		return result(map, res, HOME);
	}

	String resultRoom(ModelMap map, String res, Room room) {
		return result(map, res, "/room/" + room.name());
	}

	String resultLight(ModelMap map, String res, Bulb bulb) {
		return result(map, res, "/light/" + bulb.name());
	}
	
	String colorBright(ColorOption colorOption, BrightOption brightOption) {
		return " Set To Color " + colorOption.getLabel() + " With " + brightOption.getLabel() + " Brightness.";
	}
	
	
	@GetMapping("/all/{colorOption}/{brightOption}")
	String all(ModelMap map, @PathVariable ColorOption colorOption,
			@PathVariable BrightOption brightOption) {
		data(map);
		launch(lightRepo.getAll(), l -> lightClient.setColor(l,colorOption.getColor(),"5",  brightOption.getBrightness()));
		return resultHome(map, "All Lights" + colorBright(colorOption, brightOption));
	}
	
	
	
	
	
	
	void launch(Collection<Light> lights, Predicate<Light> function) {
		Thread.startVirtualThread(() ->{
			try(var scope = new NiceConcurrent(10L)) {
				for(var light : lights) {
					if(function.test(light)) {
						light.setLightMode(LightMode.MANUAL);
					}
				}
				scope.join();
			} catch (Exception ex) {
				log.error("launch err", ex);
			}
		});
	}
	
	
	
	@GetMapping("/rgb/all")
	String allrgb(ModelMap map, @RequestParam String colorHex, @RequestParam int bright) {
		data(map);
		var color = Color.ofHex(colorHex);
		launch(lightRepo.getAll(), x -> lightClient.setColor(x,color,"5", bright));
		return resultHome(map, "All Lights Set To Custom Color");
	}
	
	
	
	@GetMapping("/room/{room}")
	String room(ModelMap map, @PathVariable Room room) {
		data(map);
		map.put("room", room);
		return "room";
	}
	
	
	@GetMapping("/light/{bulb}")
	String light(ModelMap map, @PathVariable Bulb bulb) {
		data(map);
		map.put("light", lightRepo.get(bulb));
		return "light";
	}
	
	
	
	@GetMapping("/roomoff/{room}")
	String roomoff(ModelMap map, @PathVariable Room room) {
		data(map);
		launch(lightRepo.getAll(room), l ->  lightClient.setOff(l, "5"));
		return resultRoom(map, "All lights in " + room.getLabel() + " turned off.", room);
	}
	
	
	@GetMapping("/lightoff/{bulb}")
	String lightoff(ModelMap map, @PathVariable Bulb bulb) {
		data(map);
		var light = lightRepo.get(bulb);
		if( lightClient.setOff(light, "5") ) {
			light.setLightMode(LightMode.MANUAL);
		}
		return resultLight(map, "Light Bulb " + bulb.getDisplay() + " turned off.", bulb);
	}

	
	@GetMapping("/roomresume/{room}")
	String roomresume(ModelMap map, @PathVariable Room room) {
		data(map);
		for(var light : lightRepo.getAll(room)) {
			light.setLightMode(LightMode.AUTO);
		}
		return resultRoom(map, "All lights in " + room.getLabel() + " are on automatic schedule.", room);
	}

	
	@GetMapping("/lightresume/{bulb}")
	String lightresume(ModelMap map, @PathVariable Bulb bulb) {
		data(map);
		var light = lightRepo.get(bulb);
		light.setLightMode(LightMode.AUTO);
		return resultLight(map, "Light Bulb " + bulb.getDisplay() + " is on automatic schedule.", bulb);
	}

	
	
	@GetMapping("/roomall/{room}/{colorOption}/{brightOption}")
	String roomAll(ModelMap map, @PathVariable Room room,
			@PathVariable ColorOption colorOption,
			@PathVariable BrightOption brightOption) {
		data(map);
		map.put("room", room);
		launch(lightRepo.getAll(room), l -> lightClient.setColor(l,colorOption.getColor(), 
					"5", brightOption.getBrightness()));
		return resultRoom(map, "All Lights In " + room.getLabel() + " are set to " + colorBright(colorOption, brightOption), room); 
	}
	
	
	@GetMapping("/rgb/roomall/{room}")
	String rgbRoomAll(ModelMap map, @PathVariable Room room,
			 @RequestParam String colorHex, @RequestParam int bright
			) {
		data(map);
		var color = Color.ofHex(colorHex);
		map.put("room", room);
		launch(lightRepo.getAll(room), l -> lightClient.setColor(l,color, "5", bright));
		return resultRoom(map, "All Lights In " + room.getLabel() + " are set to custom color.", room); 
	}
	
	
	@GetMapping("/rgb/lighton/{bulb}")
	String rgblighton(ModelMap map, @PathVariable Bulb bulb,
			@RequestParam String colorHex, @RequestParam int bright) {
		data(map);
		var color = Color.ofHex(colorHex);
		var light = lightRepo.get(bulb);
		if(lightClient.setColor(light,color,"5", bright)) {
			light.setLightMode(LightMode.MANUAL);
		}
		return resultLight(map, "Light " + bulb.getDisplay() + " set to custom color.", bulb);
	}
	
	
	@GetMapping("/lighton/{bulb}/{colorOption}/{brightOption}")
	String lighton(ModelMap map, @PathVariable Bulb bulb,
			@PathVariable ColorOption colorOption,
			@PathVariable BrightOption brightOption) {
		data(map);
		var light = lightRepo.get(bulb);
		if(lightClient.setColor(light,colorOption.getColor(), 
				"5", brightOption.getBrightness())) {
			light.setLightMode(LightMode.MANUAL);
		}
		return resultLight(map, "Light " + bulb.getDisplay() + colorBright(colorOption, brightOption), bulb);
	}
	
	
	
	@GetMapping("/alloff")
	String alloff(ModelMap map) {
		data(map);
		launch(lightRepo.getAll(), l -> lightClient.setOff(l, "5") );
		return resultHome(map, "All lights have been turned off.");
	}
	
	
	
	@GetMapping("/resume")
	String resume(ModelMap map) {
		data(map);
		for(var light: lightRepo.getAll()) {
			light.setLightMode(LightMode.AUTO);
		}
		map.put("lights", lightRepo.getAll());
		return resultHome(map, "All lights have been set back to automatic schedule.");
	}
	
	
	@GetMapping("/mapping") 
	String mapping(ModelMap map) {
		data(map);
		map.put("grid", lightRepo.getGrid());
		return "mapping";
	}
	
}
