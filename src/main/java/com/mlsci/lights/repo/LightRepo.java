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
			{ TESTER, "0", "1", "NA" },

			// window wall cans
			{ "d8086c", "2", "0", "A" }, // to label
			{ "d808da", "3", "0", "B" }, 
			{ "d80953", "4", "0", "C" }, 
			{ "d80a6c", "5", "0", "D" },
			{ "d80877", "6", "0", "E" }, 
			{ "d80b9a", "7", "0", "F" }, // to label

			// bathroom wall
			{ "d80bd9", "7", "4", "G" }, // TO Label
			{ "d80995", "6", "4", "H" }, 
			{ "d80bec", "5", "4", "I" }, 
			{ "d80b5a", "4", "4", "J" }, // was W
			{ "d8096b", "3", "4", "K" }, 
			{ "d80acc", "2", "4", "L" }, // to label

			// back door
			// {"d80882", "8", "1", "M"},// would not restart after firmware update
			{ "d80c08", "8", "1", "M" }, 
			{ "d80b29", "9", "1", "N" }, 
			{ "d80a26", "9", "2", "O" },
			{ "d808ae", "9", "3", "P" }, 
			{ "d80b28", "8", "3", "Q" },

			// Front Door
			{ "d80b31", "1", "3", "R" }, 
			{ "d80a43", "0", "3", "S" }, 
			{ "d80ba6", "0", "2", "T" },
			{ "d8092e", "0", "1", "U" }, 
			{ "d80b7f", "1", "1", "V" },

			// helix area

			{ "d80XXX", "3", "5", "W" }, 
			{ "d80xxx", "2", "5" },

	};

	public void assignLocation(Light light) {
		if (light.getBulbData() != null) {
			for (var l : loc) {
				if (light.getBulbData().getTitle().contains(l[0])) {
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
		assignLocation(light);
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
		for (var light : lights.values()) {
			if (filter.test(light)) {
				list.add(light);
			}
		}
		return list;
	}

	public void generateChase() {
		var list = new ArrayList<Light>();
		int maxRow = 0;
		int maxCol = 0;
		for (var light : lights.values()) {
			if (light.getRow() > maxRow) {
				maxRow = light.getRow();
			}
			if (light.getCol() > maxCol) {
				maxCol = light.getCol();
			}
		}
		var map = new TreeMap<Double, Light>();
		double midRow = maxRow / 2.0;
		double midCol = maxCol / 2.0;
		for (var light : lights.values()) {
			double col = light.getCol() - midCol;
			double row = light.getRow() - midRow;
			double theta = Math.atan2(row, col);
			map.put(theta, light);
		}
		for (var light : map.values()) {
			list.add(light);
		}
		chase = list;
	}

	public List<Light> getChase() {
		if (chase == null) {
			generateChase();
		}
		return chase;
	}

}
