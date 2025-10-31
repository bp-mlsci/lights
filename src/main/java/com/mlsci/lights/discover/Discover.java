package com.mlsci.lights.discover;

import java.net.InetAddress;
import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.mlsci.lights.client.LightClient;
import com.mlsci.lights.repo.Light;
import com.mlsci.lights.repo.LightRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Component
@Slf4j
@RequiredArgsConstructor
class Discover {
	private final LightClient lightClient;
	private final LightRepo lightRepo;
	
	
	static final String SUBNET = "192.168.68."; 
	
	void search() {
		log.info("Searching for lights");
		try {
			try(Concurrent scope = new Concurrent()) {
				for(int x = 2; x < 255; x++) {
					var ip = SUBNET + x;
					scope.fork(() -> checkIp(ip));
				}
				scope.join();
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
			
		log.info("Done Searching for lights");
	}
	

	Boolean checkIp(String ip) {
		if(lightRepo.get(ip) == null) {
			try {
				InetAddress inetAddress = InetAddress.getByName(ip);
				if(inetAddress.isReachable(1000)) {
					var lightStatus = lightClient.getLightStatus(ip);
					if(lightStatus != null) {
						System.out.println(ip+"\n");
						System.out.println(lightStatus);
			
						var light = new Light();
						light.setLightStatus(lightStatus);
						light.setBulbData(lightClient.getBulbData(ip));
						light.setIp(ip);
						lightRepo.add(light);
					} else {
						//System.out.println(ip + " NULL STATUS ");
					}
				} else {
					//System.out.println(ip + " NO ");
				}
			} catch (Exception e) {
				//System.out.println(ip + " EX ");
				//e.printStackTrace();
			}
		}
		return true;
	}
	
	
}
