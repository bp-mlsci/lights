package com.mlsci.lights.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.mlsci.lights.repo.Light;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tools.jackson.databind.ObjectMapper;
/*
 retry: 30000
id: 8442876
event: ping
data: {"title":"Kauf Bulb d80bd9","comment":"","ota":true,"log":true,"lang":"en","esph_v":"2025.8.1","proj_n":"Kauf.RGBWW","proj_v":"1.96(u)","proj_l":"","soft_ssid":"Grange","hard_ssid":"initial_ap","has_ap":true,"free_sp":3657728,"mac_addr":"EC:64:C9:D8:0B:D9"}

event: state
data: {"id":"binary_sensor-4mib","name":"4MiB","icon":"","entity_category":0,"is_disabled_by_default":true,"value":true,"state":"ON"}

event: state
data: {"id":"light-warm_rgb","name":"Warm RGB","icon":"mdi:store-cog","entity_category":1,"is_disabled_by_default":true,"state":"OFF","color_mode":"rgbw","brightness":255,"color":{"r":255,"g":255,"b":255,"w":255},"white_value":255,"effects":["None"]}

event: state
data: {"id":"light-cold_rgb","name":"Cold RGB","icon":"mdi:store-cog-outline","entity_category":1,"is_disabled_by_default":true,"state":"ON","color_mode":"rgbw","brightness":246,"color":{"r":255,"g":255,"b":255,"w":255},"white_value":255,"effects":["None"]}

event: state
data: {"id":"light-kauf_bulb","name":"Kauf Bulb","icon":"","entity_category":0,"state":"ON","color_mode":"color_temp","brightness":91,"color":{},"color_temp":151,"effects":["None"]}

event: state
data: {"id":"sensor-uptime","name":"Uptime","icon":"mdi:timer-outline","entity_category":2,"is_disabled_by_default":true,"value":8404.428,"state":"8404 s","uom":"s"}

event: state
data: {"id":"switch-no_hass","name":"No HASS","icon":"mdi:toggle-switch-off-outline","entity_category":1,"is_disabled_by_default":true,"value":true,"state":"ON","assumed_state":false}

event: state
data: {"id":"button-restart_firmware","name":"Restart Firmware","icon":"mdi:restart","entity_category":2,"is_disabled_by_default":true}

event: state
data: {"id":"text_sensor-ip_address","name":"IP Address","icon":"","entity_category":2,"is_disabled_by_default":true,"value":"192.168.68.65","state":"192.168.68.65"}

event: state
data: {"id":"number-max_power","name":"Max Power","icon":"mdi:brightness-percent","entity_category":1,"is_disabled_by_default":true,"min_value":"1","max_value":"100","step":"1","mode":1,"uom":"%","value":"80","state":"80 %"}

event: state
data: {"id":"number-default_fade","name":"Default Fade","icon":"mdi:timer-outline","entity_category":1,"min_value":"0","max_value":"5000","step":"50","mode":1,"uom":"ms","value":"250","state":"250 ms"}

event: state
data: {"id":"select-effect","name":"Effect","icon":"mdi:string-lights","entity_category":1,"value":"None","state":"None","option":["None","WLED / DDP"]}

event: state
data: {"id":"select-ddp_debug","name":"DDP Debug","icon":"mdi:play-network","entity_category":2,"is_disabled_by_default":true,"value":"Print no packets","state":"Print no packets","option":["Print no packets","Print imperfect packets","Print all packets"]}

event: state
data: {"id":"select-power_on_state","name":"Power On State","icon":"mdi:restart-alert","entity_category":1,"value":"Always On - Last Value","state":"Always On - Last Value","option":["Restore Power Off State","Always On - Last Value","Always On - Bright White","Always Off","Invert State"]}

 */
@Component
@Slf4j
@RequiredArgsConstructor
public class LightClient {
	private final RestTemplate restTemplate;
	private final ObjectMapper objectMapper;
	
	public LightStatus getLightStatus(String ip) {
		var url = "http://" + ip + "/light/kauf_bulb";
		//System.out.print(url);
		try {
			var ans = restTemplate.exchange(url, HttpMethod.GET, null, LightStatus.class);
			//System.out.println(" " + ans.getStatusCode());
			return ans.getBody();
		} catch(Exception ex) {
			//ex.printStackTrace();
			return null;
		}
		
	}

	
	
	   public BulbData getBulbData(String ip) {
	        String urlString = "http://" + ip + "/events"; 
	        int linesToRead = 7; // Number of lines to read
	        BulbData bulbData = null;
	        try {
	            URL url = new URI(urlString).toURL();
	            // Using try-with-resources ensures the BufferedReader is automatically closed
	            try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
	                String line;
	                int count = 0;
	                var next = false;
	                while ((line = reader.readLine()) != null && count < linesToRead) {
	                    if(next) {
	                    	var json = line.substring("data: ".length());
	                    	bulbData = objectMapper.readValue(json, BulbData.class);
	                    	next = false;		
	                    }
	                	if(line.startsWith("event: ping")) {
	                    	next = true;
	                    }
	                    
	                    	
	                    count++;
	                }
	            }
	            return bulbData;
	            
	            
	            
	        } catch (Exception e) {
	            System.err.println("Error reading from URL: " + e.getMessage());
	            return null;
	        }
	        
	        
	    }



	   public void setColor(Light light, Color color, String transition, int brightness) {
		   var url = "http://" + light.getIp() + "/light/kauf_bulb/turn_on?r="+color.getR() + "&g=" + color.getG() + "&b=" + color.getB()  + "&transition=" + transition + "&brightness=" + brightness;
		   
		   var ans = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
		   log.info(ans.getStatusCode().toString());
		
	   }
	
}
