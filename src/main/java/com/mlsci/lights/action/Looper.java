package com.mlsci.lights.action;

import java.util.ArrayList;
import java.util.List;

import com.mlsci.lights.client.Color;
import com.mlsci.lights.client.LightClient;
import com.mlsci.lights.discover.Concurrent;
import com.mlsci.lights.repo.LightRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class Looper implements Action {
	private final LightClient lightClient;
	private final LightRepo lightRepo;
	private final String name;
	
	private List<ColorTime> colorTimes = new ArrayList<ColorTime>();
	
	
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
			var lights = lightRepo.getAll();
			try(var scope = new Concurrent()) {
				for(var light : lights) {
					scope.fork(() -> {
						lightClient.setColor(light, color, "4", brightness);
						return true;
					});
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
			if(index >= colorTimes.size()) {
				log.info(name + " Ended");
				currentStep.setEnded(true);
				return currentStep;
			}
			log.info(name + " on " + index);
			currentStep.setIndex(index);
			colorAll(colorTimes.get(index).color(), colorTimes.get(index).brightness());
			currentStep.setUntilTimeMillis((System.currentTimeMillis() - 10L) + (1000L * colorTimes.get(index).seconds()));
			
		} else {
			log.info(name + " Looper No Change " + currentStep.getIndex());
		}
		return currentStep;
	}

	public void add(Color color, long seconds, int brightness) {
		colorTimes.add(new ColorTime(color,seconds, brightness));
		
	}

}
