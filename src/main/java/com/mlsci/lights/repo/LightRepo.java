package com.mlsci.lights.repo;

import java.util.ArrayList;
import java.util.Collection;
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
	private Map<Bulb, Light> lights = new TreeMap<Bulb, Light>();
	private List<Light> chase = null;

	


	public void add(Light light) {
		log.info(light.toString());
		//assignLocation(light);
		lights.put(light.getBulb(), light);

		chase = null;
	}

	public Light get(Bulb bulb) {
		var l = lights.get(bulb);
		return l;
	}

	public Collection<Light> getAll() {
		return lights.values();
	}

	public List<Light> getFilter(Predicate<Light> filter) {
		var list = new ArrayList<Light>();
		for (var light : lights.values()) {
			if (filter.test(light)) {
				list.add(light);
			}
		}
		return list;
	}

	public void generateChase(Room room) {
		var list = new ArrayList<Light>();
		int maxRow = 0;
		int maxCol = 0;
		var filt = getAll(room);
		for (var light : filt) {
			if (light.getBulb().getRow() > maxRow) {
				maxRow = light.getBulb().getRow();
			}
			if (light.getBulb().getCol() > maxCol) {
				maxCol = light.getBulb().getCol();
			}
		}
		var map = new TreeMap<Double, Light>();
		double midRow = maxRow / 2.0;
		double midCol = maxCol / 2.0;
		for (var light : lights.values()) {
			double col = light.getBulb().getCol() - midCol;
			double row = light.getBulb().getRow() - midRow;
			double theta = Math.atan2(row, col);
			map.put(theta, light);
		}
		for (var light : map.values()) {
			list.add(light);
		}
		chase = list;
	}

	public List<Light> getChase(Room room) {
		if (chase == null) {
			generateChase(room);
		}
		return chase;
	}

	public List<Light> getAll(Room room) {		
		return getFilter(l -> room.equals(l.getBulb().getRoom()));
	}

}
