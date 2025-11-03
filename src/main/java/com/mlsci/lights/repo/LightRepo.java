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
	public static final String TESTER = "tester";
	private Map<String, Light> lights = new HashMap<String, Light>();
	private List<Light> chase = null;
	
	private static final String[][] loc = {
			{TESTER,"0", "1"},
			
			// window wall cans
			{"d8086c", "2", "0"},
			{"d80xxx", "3", "0"},
			{"d80xxx", "4", "0"},
			{"d80xxx", "5", "0"},
			{"d80xxx", "6", "0"},
			{"d80b9a", "7", "0"},
			
			// back door
			{"d80xxx", "8", "1"},
			{"d80xxx", "9", "1"},
			{"d80xxx", "9", "2"},
			{"d80xxx", "9", "3"},
			{"d80xxx", "8", "3"},
			
			// bathroom wall
			{"d80bd9", "7", "4"},
			{"d80xxx", "6", "4"},
			{"d80xxx", "5", "4"},
			{"d80xxx", "4", "4"},
			{"d80xxx", "3", "4"},
			{"d80acc", "2", "4"},
			
			
			// Front Door
			{"d80xxx", "1", "3"},
			{"d80xxx", "0", "3"},
			{"d80xxx", "0", "2"},
			{"d80xxx", "0", "1"},
			{"d80xxx", "1", "1"},
			
			// helix area
			
			{"d80xxx", "3", "5"},
			{"d80xxx", "2", "5"},
			
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
