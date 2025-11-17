package com.mlsci.lights.action;

import java.util.List;

import com.mlsci.lights.client.Color;
import com.mlsci.lights.client.LightClient;
import com.mlsci.lights.repo.LightRepo;
import com.mlsci.lights.repo.Room;

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
	private final Room room;
	
	

	
	
	@Override
	public String getName() { return name; }
	

	@Override
	public Step getFirstStep() {
		var step = new Step();
		step.setIndex(0);
		step.setUntilTimeMillis(System.currentTimeMillis() - 10L);
		return step;
	}
	
	
	
	@Override
	public Step doStep(Step currentStep) {
		if(System.currentTimeMillis() > currentStep.getUntilTimeMillis()) {
			var index = currentStep.getIndex();
			setLights(index);
			currentStep.setIndex(index+offset);
			currentStep.setCount(currentStep.getCount() + 1);
			currentStep.setEnded(currentStep.getCount() > (lightRepo.getChase(getRoom()).size() * 5));
			currentStep.setUntilTimeMillis(System.currentTimeMillis() + 1200L);
		} else {
			log.info(name + " Chase No Change " + currentStep.getIndex());
		}
		return currentStep;
	}


	void setLights(int index) {
		var chase = lightRepo.getChase(getRoom());
		if(chase.size() == 0) {
			return;
		}
		var i = index % chase.size();
		
		if(i < 0 ) {
			i = -i;
		}
		for(var color : colorList) {
			if( i < 0 ) {
				i = chase.size() - 1;
			}
			if(i >= chase.size()) {
				i = 0;
			}
			var light = chase.get(i);
			lightClient.setColor(light, color, "0.5", 200);
			i--;
		}
	}


	@Override
	public Room getRoom() {
		return room;
	}


	
}
