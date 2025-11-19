package com.mlsci.lights.repo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Predicate;

import org.springframework.stereotype.Component;

import com.mlsci.lights.status.Grid;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LightRepo {
	private Map<Bulb, Light> lights = init();
	private List<Light> chase = null;
	private Grid grid;

	static TreeMap<Bulb, Light> init() {
		var map = new TreeMap<Bulb, Light>();
		for(var bulb : Bulb.values()) {
			Light l = new Light();
			l.setBulb(bulb);
			map.put(bulb, l);
		}
		return map;
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

	public void generateChase(Room room, LightMode lightMode) {
		var list = new ArrayList<Light>();
		int maxRow = 0;
		int maxCol = 0;
		var filt = getAll(room, lightMode);
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

	
	public List<Light> getChase(Room room, LightMode lightMode) {
		if (chase == null) {
			generateChase(room, lightMode);
		}
		return chase;
	}

	public List<Light> getAll(Room room) {		
		return getFilter(l -> room.equals(l.getBulb().getRoom()));
	}

	public List<Light> getAll(Room room, LightMode lightMode) {		
		return getFilter(l -> room.equals(l.getBulb().getRoom()) &&
				lightMode.equals(l.getLightMode()));
	}
	
	public Grid getGrid() {
		if( grid == null) {
			var maxRow = 0;
			var maxCol = 0;
			for(var b : Bulb.values()) {
				maxRow = Math.max(maxRow, b.getRow());
				maxCol = Math.max(maxCol, b.getCol());
			}
			grid = new Grid(maxRow, maxCol);
			for(var bulb : Bulb.values()) { 
				grid.add(lights.get(bulb)); 
			}
		}
		return grid;
	}
	
}
