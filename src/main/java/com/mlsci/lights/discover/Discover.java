package com.mlsci.lights.discover;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.mlsci.lights.client.LightClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Component
@Slf4j
@RequiredArgsConstructor
class Discover {
	private final LightClient lightClient;
	
	
	static final String SUBNET = "192.168.68."; 
	
	void search() {
		log.info("Searching for lights");
		var ips = new ArrayList<String>();
		for(int x = 2; x < 255; x++) {
			var ip = SUBNET + x;
			ips.add(ip);
		}
		ips.parallelStream().forEach(x -> {	
	
				var ans = lightClient.getLightStatus(x);
				if(ans != null) {
					System.out.println(x+"\n");
					System.out.println(ans);
				}
		});
			
		log.info("Done Searching for lights");
	}

	
	

	
	
}
