package com.mlsci.lights.repo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LightRepo {
	private Map<String, Light> lights = new HashMap<String, Light>();
	
	
	
	public void add(Light light) {
		log.info(light.toString());
		lights.put(light.getIp(), light);
	}
	
	
	public Light get(String ip) {
		var l = lights.get(ip);
		return l;
	}


	public Collection<Light> getAll() {
		return lights.values();
	}
	
	
	public List<Light> getFilter(Predicate<Light> filter) {
		var list = new ArrayList<Light>();
		for(var light : lights.values()) {
			if( filter.test(light)) {
				list.add(light);
			}
		}
		return list;
	}

}
