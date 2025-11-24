package com.mlsci.lights.action;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.mlsci.lights.NiceConcurrent;
import com.mlsci.lights.client.LightClient;
import com.mlsci.lights.repo.LightRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class KeepAliveSchedule {
	private final LightClient lightClient;
	private final LightRepo lightRepo;
	
	
	
	
	
	@Scheduled(fixedDelay = 500L)
	void keepAllAlive() {
		try (var scope = new NiceConcurrent(15L)) {
			for(var light: lightRepo.getAll()) {
				scope.fork(() -> {
					lightClient.keepAlive(light);
					return true;
					}	
				);
			}
			scope.join();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("keep alive failed ", e);
		}
	}


	
}
