package com.mlsci.lights.action;

import java.util.List;

import com.mlsci.lights.repo.Room;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class RoomAction {
	private Room room;
	private int index = 0;
	private List<Action> actions;
	private Step currentStep;
	private Action currentAction;

	
	void nextAction() {
		currentAction = actions.get(index);
		index++;
		if(index >= actions.size()) {
			index = 0;
		}
		log.info(currentAction.getName());
		currentStep = currentAction.getFirstStep();
	}
	
	
	public void clockTick() {
		if(currentAction == null) {
			nextAction();
		}
		currentStep = currentAction.doStep(currentStep);
		if(currentStep.isEnded()) {
			nextAction();
		}
	}
}
