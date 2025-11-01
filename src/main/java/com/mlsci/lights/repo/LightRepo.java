package com.mlsci.lights.repo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Predicate;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LightRepo {
	public static final String _080AAC = "080aac";
	private Map<String, Light> lights = new HashMap<String, Light>();
	private List<Light> chase = null;
	
	private static final String[][] loc = {
			{_080AAC,"0", "1"},
			{"080db9", "2", "3"}
			
	};
	
	public void assignLocation(Light light) {
		if(light.getBulbData() != null) {
			for(var l : loc) {
				if(light.getBulbData().getTitle().contains(l[0])) {
					int r = Integer.valueOf(l[1]);
					light.setRow(r);
					int c = Integer.valueOf(l[2]);
					light.setCol(c);
				}
			}
		}
	}
	
	
	public void add(Light light) {
		log.info(light.toString());
		lights.put(light.getIp(), light);
		
		chase = null;
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

	
	public void generateChase() {
		var list = new ArrayList<Light>();
		int maxRow = 0;
		int maxCol = 0;
		for(var light : lights.values()) {
			if(light.getRow() > maxRow) {
				maxRow = light.getRow();
			}
			if(light.getCol() > maxCol) {
				maxCol = light.getCol();
			}
		}
		var map = new TreeMap<Double, Light>();
		double midRow = maxRow / 2.0;
		double midCol = maxCol / 2.0;
		for(var light : lights.values()) {
			double col = light.getCol() - midCol;
			double row = light.getRow() - midRow;
			double theta = Math.atan2(row, col);
			map.put(theta, light);
		}
		for(var light : map.values()) {
			list.add(light);
		}
		chase = list;
	}

	public List<Light> getChase() {
		if(chase == null) {
			generateChase();
		}
		return chase;
	}

}
