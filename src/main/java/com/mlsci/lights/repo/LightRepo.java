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
			{ "d8086c", "2", "0", "A", "EC-64-C9-D8-0B-6C", "192.168.68.200"}, // to label
			{ "d808da", "3", "0", "B", "EC-64-C9-D8-08-DA", "192.168.68.201" }, 
			{ "d80953", "4", "0", "C", "EC-64-C9-D8-09-53", "192.168.68.202" }, 
			{ "d80a6c", "5", "0", "D", "EC-64-C9-D8-0A-6C", "192.168.68.203" },// Check again!
			{ "d80877", "6", "0", "E", "EC-64-C9-D8-08-77", "192.168.68.204" },// Check me
			{ "d80b9a", "7", "0", "F", "EC-64-C9-D8-0B-9A", "192.168.68.205"  }, // to label  Check

			// bathroom wall
			{ "d80bd9", "7", "4", "G", "EC-64-C9-D8-0B-D9", "192.168.68.206" }, // TO Label
			{ "d80995", "6", "4", "H", "EC-64-C9-D8-09-95", "192.168.68.207" }, 
			{ "d80bec", "5", "4", "I", "EC-64-C9-D8-0B-EC", "192.168.68.208" }, 
			{ "d80b5a", "4", "4", "J", "EC-64-C9-D8-0B-5A", "192.168.68.209" }, // was W
			{ "d8096b", "3", "4", "K", "EC-64-C9-D8-09-6B", "192.168.68.210" }, 
			{ "d80acc", "2", "4", "L", "EC-64-C9-D8-0A-CC", "192.168.68.211" }, // to label Check

			// back door
			// {"d80882", "8", "1", "M"},// would not restart after firmware update
			{ "d80c08", "8", "1", "M", "EC-64-C9-D8-0C-08", "192.168.68.212" }, // CHECK
			{ "d80b29", "9", "1", "N", "EC-64-C9-D8-0B-29", "192.168.68.213" }, // Check
			{ "d80a26", "9", "2", "O", "EC-64-C9-D8-0A-26", "192.168.68.214" }, // check
			{ "d808ae", "9", "3", "P", "EC-64-C9-D8-08-AE", "192.168.68.215" }, // CHECK
			{ "d80b28", "8", "3", "Q", "EC-64-C9-D8-0B-28", "192.168.68.216" }, // CHECK

			// Front Door
			{ "d80b31", "1", "3", "R", "EC-64-C9-D8-0B-31", "192.168.68.217" }, 
			{ "d80a43", "0", "3", "S", "EC-64-C9-D8-0A-43", "192.168.68.218" }, 
			{ "d80ba6", "0", "2", "T" },
			{ "d8092e", "0", "1", "U" }, 
			{ "d80b7f", "1", "1", "V" },

			// helix area

			// d80a21 failed on reload firmware	
			{ "d80b3d", "3", "5", "W" }, 
			{ "d80936", "2", "5", "X" },
			{ "d809db", "4", "5", "Y" },

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

/*
 
 Light(ip=192.168.68.82, lightStatus=LightStatus(id=light-kauf_bulb, name=null, state=ON, color_mode=rgb, brightness=150, color=Color(r=0, g=255, b=0, w=null, color_temp=null), white_value=null, color_temp=null), bulbData=BulbData(title=Kauf Bulb d80b28, comment=, ota=true, log=true, lang=en, esph_v=2025.8.1, proj_n=Kauf.RGBWW, proj_v=1.96(u), proj_l=, mac_addr=EC:64:C9:D8:0B:28), row=8, col=3, lastCommand=http://192.168.68.82/light/kauf_bulb/turn_on?r=0&g=255&b=0&transition=4&brightness=80, lastResult=200 OK)

Light(ip=192.168.68.93, lightStatus=LightStatus(id=light-kauf_bulb, name=null, state=OFF, color_mode=rgb, brightness=200, color=Color(r=255, g=0, b=0, w=null, color_temp=null), white_value=null, color_temp=null), bulbData=BulbData(title=Kauf Bulb d80953, comment=, ota=true, log=true, lang=en, esph_v=2025.8.1, proj_n=Kauf.RGBWW, proj_v=1.96(u), proj_l=, mac_addr=EC:64:C9:D8:09:53), row=4, col=0, lastCommand=http://192.168.68.93/light/kauf_bulb/turn_on?r=0&g=255&b=0&transition=4&brightness=80, lastResult=200 OK)

Light(ip=192.168.68.60, lightStatus=LightStatus(id=light-kauf_bulb, name=null, state=ON, color_mode=rgb, brightness=150, color=Color(r=0, g=255, b=0, w=null, color_temp=null), white_value=null, color_temp=null), bulbData=BulbData(title=Kauf Bulb d80995, comment=, ota=true, log=true, lang=en, esph_v=2025.8.1, proj_n=Kauf.RGBWW, proj_v=1.96(u), proj_l=, mac_addr=EC:64:C9:D8:09:95), row=6, col=4, lastCommand=http://192.168.68.60/light/kauf_bulb/turn_on?r=0&g=255&b=0&transition=4&brightness=80, lastResult=200 OK)

Light(ip=192.168.68.81, lightStatus=LightStatus(id=light-kauf_bulb, name=null, state=OFF, color_mode=rgb, brightness=200, color=Color(r=255, g=0, b=0, w=null, color_temp=null), white_value=null, color_temp=null), bulbData=BulbData(title=Kauf Bulb d80ba6, comment=, ota=true, log=true, lang=en, esph_v=2025.8.1, proj_n=Kauf.RGBWW, proj_v=1.96(u), proj_l=, mac_addr=EC:64:C9:D8:0B:A6), row=0, col=2, lastCommand=http://192.168.68.81/light/kauf_bulb/turn_on?r=0&g=255&b=0&transition=4&brightness=80, lastResult=200 OK)

Light(ip=192.168.68.92, lightStatus=LightStatus(id=light-kauf_bulb, name=null, state=OFF, color_mode=rgb, brightness=200, color=Color(r=255, g=0, b=0, w=null, color_temp=null), white_value=null, color_temp=null), bulbData=BulbData(title=Kauf Bulb d80a6c, comment=, ota=true, log=true, lang=en, esph_v=2025.8.1, proj_n=Kauf.RGBWW, proj_v=1.96(u), proj_l=, mac_addr=EC:64:C9:D8:0A:6C), row=5, col=0, lastCommand=http://192.168.68.92/light/kauf_bulb/turn_on?r=0&g=255&b=0&transition=4&brightness=80, lastResult=200 OK)

Light(ip=192.168.68.91, lightStatus=LightStatus(id=light-kauf_bulb, name=null, state=ON, color_mode=rgb, brightness=150, color=Color(r=0, g=255, b=0, w=null, color_temp=null), white_value=null, color_temp=null), bulbData=BulbData(title=Kauf Bulb d80b9a, comment=, ota=true, log=true, lang=en, esph_v=2025.8.1, proj_n=Kauf.RGBWW, proj_v=1.96(u), proj_l=, mac_addr=EC:64:C9:D8:0B:9A), row=7, col=0, lastCommand=http://192.168.68.91/light/kauf_bulb/turn_on?r=0&g=255&b=0&transition=4&brightness=80, lastResult=200 OK)

Light(ip=192.168.68.80, lightStatus=LightStatus(id=light-kauf_bulb, name=null, state=OFF, color_mode=rgb, brightness=200, color=Color(r=255, g=0, b=0, w=null, color_temp=null), white_value=null, color_temp=null), bulbData=BulbData(title=Kauf Bulb d80b31, comment=, ota=true, log=true, lang=en, esph_v=2025.8.1, proj_n=Kauf.RGBWW, proj_v=1.96(u), proj_l=, mac_addr=EC:64:C9:D8:0B:31), row=1, col=3, lastCommand=http://192.168.68.80/light/kauf_bulb/turn_on?r=0&g=255&b=0&transition=4&brightness=80, lastResult=200 OK)

Light(ip=192.168.68.90, lightStatus=LightStatus(id=light-kauf_bulb, name=null, state=OFF, color_mode=rgb, brightness=150, color=Color(r=0, g=255, b=0, w=null, color_temp=null), white_value=null, color_temp=null), bulbData=BulbData(title=Kauf Bulb d80877, comment=, ota=true, log=true, lang=en, esph_v=2025.8.1, proj_n=Kauf.RGBWW, proj_v=1.96(u), proj_l=, mac_addr=EC:64:C9:D8:08:77), row=6, col=0, lastCommand=http://192.168.68.90/light/kauf_bulb/turn_on?r=0&g=255&b=0&transition=4&brightness=80, lastResult=200 OK)

Light(ip=192.168.68.79, lightStatus=LightStatus(id=light-kauf_bulb, name=null, state=OFF, color_mode=rgb, brightness=200, color=Color(r=255, g=0, b=0, w=null, color_temp=null), white_value=null, color_temp=null), bulbData=BulbData(title=Kauf Bulb d80b7f, comment=, ota=true, log=true, lang=en, esph_v=2025.8.1, proj_n=Kauf.RGBWW, proj_v=1.96(u), proj_l=, mac_addr=EC:64:C9:D8:0B:7F), row=1, col=1, lastCommand=http://192.168.68.79/light/kauf_bulb/turn_on?r=0&g=255&b=0&transition=4&brightness=80, lastResult=200 OK)

Light(ip=192.168.68.89, lightStatus=LightStatus(id=light-kauf_bulb, name=null, state=OFF, color_mode=rgb, brightness=200, color=Color(r=255, g=0, b=0, w=null, color_temp=null), white_value=null, color_temp=null), bulbData=BulbData(title=Kauf Bulb d8086c, comment=, ota=true, log=true, lang=en, esph_v=2025.8.1, proj_n=Kauf.RGBWW, proj_v=1.96(u), proj_l=, mac_addr=EC:64:C9:D8:08:6C), row=2, col=0, lastCommand=http://192.168.68.89/light/kauf_bulb/turn_on?r=0&g=255&b=0&transition=4&brightness=80, lastResult=200 OK)

Light(ip=192.168.68.78, lightStatus=LightStatus(id=light-kauf_bulb, name=null, state=ON, color_mode=rgb, brightness=150, color=Color(r=0, g=255, b=0, w=null, color_temp=null), white_value=null, color_temp=null), bulbData=BulbData(title=Kauf Bulb d80c08, comment=, ota=true, log=true, lang=en, esph_v=2025.8.1, proj_n=Kauf.RGBWW, proj_v=1.96(u), proj_l=, mac_addr=EC:64:C9:D8:0C:08), row=8, col=1, lastCommand=http://192.168.68.78/light/kauf_bulb/turn_on?r=0&g=255&b=0&transition=4&brightness=80, lastResult=200 OK)

Light(ip=192.168.68.77, lightStatus=LightStatus(id=light-kauf_bulb, name=null, state=ON, color_mode=rgb, brightness=150, color=Color(r=0, g=255, b=0, w=null, color_temp=null), white_value=null, color_temp=null), bulbData=BulbData(title=Kauf Bulb d80b29, comment=, ota=true, log=true, lang=en, esph_v=2025.8.1, proj_n=Kauf.RGBWW, proj_v=1.96(u), proj_l=, mac_addr=EC:64:C9:D8:0B:29), row=9, col=1, lastCommand=http://192.168.68.77/light/kauf_bulb/turn_on?r=0&g=255&b=0&transition=4&brightness=80, lastResult=200 OK)

Light(ip=192.168.68.76, lightStatus=LightStatus(id=light-kauf_bulb, name=null, state=OFF, color_mode=rgb, brightness=200, color=Color(r=255, g=0, b=0, w=null, color_temp=null), white_value=null, color_temp=null), bulbData=BulbData(title=Kauf Bulb d8092e, comment=, ota=true, log=true, lang=en, esph_v=2025.8.1, proj_n=Kauf.RGBWW, proj_v=1.96(u), proj_l=, mac_addr=EC:64:C9:D8:09:2E), row=0, col=1, lastCommand=http://192.168.68.76/light/kauf_bulb/turn_on?r=0&g=255&b=0&transition=4&brightness=80, lastResult=200 OK)

Light(ip=192.168.68.75, lightStatus=LightStatus(id=light-kauf_bulb, name=null, state=ON, color_mode=rgb, brightness=150, color=Color(r=0, g=255, b=0, w=null, color_temp=null), white_value=null, color_temp=null), bulbData=BulbData(title=Kauf Bulb d80a26, comment=, ota=true, log=true, lang=en, esph_v=2025.8.1, proj_n=Kauf.RGBWW, proj_v=1.96(u), proj_l=, mac_addr=EC:64:C9:D8:0A:26), row=9, col=2, lastCommand=http://192.168.68.75/light/kauf_bulb/turn_on?r=0&g=255&b=0&transition=4&brightness=80, lastResult=200 OK)

Light(ip=192.168.68.63, lightStatus=LightStatus(id=light-kauf_bulb, name=null, state=ON, color_mode=rgb, brightness=200, color=Color(r=255, g=0, b=0, w=null, color_temp=null), white_value=null, color_temp=null), bulbData=BulbData(title=Kauf Bulb d80acc, comment=, ota=true, log=true, lang=en, esph_v=2025.8.1, proj_n=Kauf.RGBWW, proj_v=1.96(u), proj_l=, mac_addr=EC:64:C9:D8:0A:CC), row=2, col=4, lastCommand=http://192.168.68.63/light/kauf_bulb/turn_on?r=0&g=255&b=0&transition=4&brightness=80, lastResult=200 OK)

Light(ip=192.168.68.74, lightStatus=LightStatus(id=light-kauf_bulb, name=null, state=OFF, color_mode=rgb, brightness=200, color=Color(r=255, g=0, b=0, w=null, color_temp=null), white_value=null, color_temp=null), bulbData=BulbData(title=Kauf Bulb d80a43, comment=, ota=true, log=true, lang=en, esph_v=2025.8.1, proj_n=Kauf.RGBWW, proj_v=1.96(u), proj_l=, mac_addr=EC:64:C9:D8:0A:43), row=0, col=3, lastCommand=http://192.168.68.74/light/kauf_bulb/turn_on?r=0&g=255&b=0&transition=4&brightness=80, lastResult=200 OK)

Light(ip=192.168.68.62, lightStatus=LightStatus(id=light-kauf_bulb, name=null, state=ON, color_mode=rgb, brightness=150, color=Color(r=0, g=255, b=0, w=null, color_temp=null), white_value=null, color_temp=null), bulbData=BulbData(title=Kauf Bulb d80bec, comment=, ota=true, log=true, lang=en, esph_v=2025.8.1, proj_n=Kauf.RGBWW, proj_v=1.96(u), proj_l=, mac_addr=EC:64:C9:D8:0B:EC), row=5, col=4, lastCommand=http://192.168.68.62/light/kauf_bulb/turn_on?r=0&g=255&b=0&transition=4&brightness=80, lastResult=200 OK)

Light(ip=192.168.68.73, lightStatus=LightStatus(id=light-kauf_bulb, name=null, state=ON, color_mode=rgb, brightness=150, color=Color(r=0, g=255, b=0, w=null, color_temp=null), white_value=null, color_temp=null), bulbData=BulbData(title=Kauf Bulb d808ae, comment=, ota=true, log=true, lang=en, esph_v=2025.8.1, proj_n=Kauf.RGBWW, proj_v=1.96(u), proj_l=, mac_addr=EC:64:C9:D8:08:AE), row=9, col=3, lastCommand=http://192.168.68.73/light/kauf_bulb/turn_on?r=0&g=255&b=0&transition=4&brightness=80, lastResult=200 OK)

Light(ip=192.168.68.61, lightStatus=LightStatus(id=light-kauf_bulb, name=null, state=ON, color_mode=rgb, brightness=150, color=Color(r=0, g=255, b=0, w=null, color_temp=null), white_value=null, color_temp=null), bulbData=BulbData(title=Kauf Bulb d80b5a, comment=, ota=true, log=true, lang=en, esph_v=2025.8.1, proj_n=Kauf.RGBWW, proj_v=1.96(u), proj_l=, mac_addr=EC:64:C9:D8:0B:5A), row=4, col=4, lastCommand=http://192.168.68.61/light/kauf_bulb/turn_on?r=0&g=255&b=0&transition=4&brightness=80, lastResult=200 OK)

Light(ip=192.168.68.94, lightStatus=LightStatus(id=light-kauf_bulb, name=null, state=OFF, color_mode=rgb, brightness=200, color=Color(r=255, g=0, b=0, w=null, color_temp=null), white_value=null, color_temp=null), bulbData=BulbData(title=Kauf Bulb d808da, comment=, ota=true, log=true, lang=en, esph_v=2025.8.1, proj_n=Kauf.RGBWW, proj_v=1.96(u), proj_l=, mac_addr=EC:64:C9:D8:08:DA), row=3, col=0, lastCommand=http://192.168.68.94/light/kauf_bulb/turn_on?r=0&g=255&b=0&transition=4&brightness=80, lastResult=200 OK)
 
*/