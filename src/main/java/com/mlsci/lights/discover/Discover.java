package com.mlsci.lights.discover;

import java.net.InetAddress;

import org.springframework.stereotype.Component;

import com.mlsci.lights.client.LightClient;
import com.mlsci.lights.repo.Bulb;
import com.mlsci.lights.repo.Light;
import com.mlsci.lights.repo.LightRepo;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Component
@Slf4j
@RequiredArgsConstructor
class Discover {
	private final LightClient lightClient;
	private final LightRepo lightRepo;
	private boolean active = true;
	
	@PreDestroy
	public void pause() {
		log.info("Pausing discovery");
		active = false;
	}
	
	
	@PostConstruct 
	public void dummy() {
		for(var bulb : Bulb.values()) {
			var l = new Light();
			l.setBulb(bulb);
			lightRepo.add(l);
		}
	}
	
	public void resume() {
		active = true;
	}
	
	void search() {
		log.info("Searching for lights");
		
		for(var bulb : Bulb.values()) {
			if(active) {
				checkIp(bulb);
			} else {
				log.info("Skipping " + bulb.name());
			}
		}	
		log.info("Done Searching for lights");
	}
	

	Boolean checkIp(Bulb bulb) {
		if(lightRepo.get(bulb) == null) {
			try {
				InetAddress inetAddress = InetAddress.getByName(bulb.getIp());
				if(inetAddress.isReachable(3000)) {
					var lightStatus = lightClient.getLightStatus(bulb.getIp());
					if(lightStatus != null) {
						System.out.println(bulb.name()+"\n");
						System.out.println(lightStatus);
			
						var light = new Light();
						light.setLightStatus(lightStatus);
						light.setBulbData(lightClient.getBulbData(bulb.getIp()));
						light.setBulb(bulb);
						lightRepo.add(light);
					} else {
						//System.out.println(ip + " NULL STATUS ");
					}
				} else {
					System.out.println("Cannot Find Bulb " + bulb.name());
				}
			} catch (Exception e) {
				//System.out.println(ip + " EX ");
				//e.printStackTrace();
			}
		}
		return true;
	}
	
	
}
