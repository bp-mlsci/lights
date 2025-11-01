package com.mlsci.lights.action;

import java.util.ArrayList;
import java.util.List;

import com.mlsci.lights.client.Color;
import com.mlsci.lights.client.LightClient;
import com.mlsci.lights.discover.Concurrent;
import com.mlsci.lights.repo.Light;
import com.mlsci.lights.repo.LightRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class Chase implements Action {
	private final LightClient lightClient;
	private final LightRepo lightRepo;
	private final String name;
	private final List<Color> colorList;
	private final int offset;
	
	
	private List<Light> lights; 
	
	
	@Override
	public String getName() { return name; }
	

	@Override
	public Step getFirstStep() {
		var step = new Step();
		step.setIndex(0);
		step.setUntilTimeMillis(System.currentTimeMillis() - 10L);
		lights = lightRepo.getChase();
		return step;
	}
	
	
	
	@Override
	public Step doStep(Step currentStep) {
		if(System.currentTimeMillis() > currentStep.getUntilTimeMillis()) {
			var index = currentStep.getIndex();
			setLights(index);
			currentStep.setIndex(index+offset);
			currentStep.setCount(currentStep.getCount() + 1);
			currentStep.setEnded(currentStep.getCount() > (lights.size() * 5));
			currentStep.setUntilTimeMillis(System.currentTimeMillis() + 1200L);
		} else {
			log.info(name + " Chase No Change " + currentStep.getIndex());
		}
		return currentStep;
	}


	void setLights(int index) {
		var i = index;
		for(var color : colorList) {
			var light = lights.get(i % lights.size());
			lightClient.setColor(light, color, "0.5", 200);
			i--;
		}
	}

	
}
