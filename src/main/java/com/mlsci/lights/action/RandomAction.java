package com.mlsci.lights.action;

import java.time.Duration;

import com.mlsci.lights.Concurrent;
import com.mlsci.lights.client.Color;
import com.mlsci.lights.client.LightClient;
import com.mlsci.lights.client.RandomColor;
import com.mlsci.lights.repo.LightMode;
import com.mlsci.lights.repo.LightRepo;
import com.mlsci.lights.repo.Room;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class RandomAction implements Action {
	private final LightClient lightClient;
	private final LightRepo lightRepo;
	private final String name;
	private final Room room;
	private final int count;
	
	private final RandomColor randomColor = new RandomColor();
	
	
	@Override
	public String getName() { return name; }
	

	@Override
	public Step getFirstStep() {
		var step = new Step();
		step.setIndex(-1);
		step.setUntilTimeMillis(System.currentTimeMillis() - 10L);
		return step;
	}


	
	void colorAll(Color color, int brightness) {
		try {
			var lights = lightRepo.getAll(getRoom(), LightMode.AUTO);
			var delay = 0L;
			try(var scope = new Concurrent()) {
				for(var light : lights) {
					var del = Duration.ofMillis(delay);//stagger
					scope.fork(() -> {
						Thread.sleep(del);
						lightClient.setColor(light, color, "5", brightness);
						return true;
					});
					delay += 35L;
				}
				scope.join();
			}
		} catch (Exception ex) {
			log.error("color all fail",ex);
		}
		
	}
	
	
	
	@Override
	public Step doStep(Step currentStep) {
		if(System.currentTimeMillis() > currentStep.getUntilTimeMillis()) {
			var index = currentStep.getIndex();
			index++; 
			if(index >= count) {
				log.info(name + " Ended");
				currentStep.setEnded(true);
				return currentStep;
			}
			log.info(name + " on " + index);
			currentStep.setIndex(index);
			colorAll(randomColor, 250);
			currentStep.setUntilTimeMillis((System.currentTimeMillis() - 10L) + (1000L * 15L));		
		} else {
			
		}
		return currentStep;
	}

	

	@Override
	public Room getRoom() {
		return room;
	}

}
