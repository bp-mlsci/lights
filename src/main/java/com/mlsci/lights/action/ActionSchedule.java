package com.mlsci.lights.action;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class ActionSchedule {
	private boolean clockEnabled = true;
	private final List<Action> actions;
	
	private Action currentAction;
	private int actionIndex = 0;
	private Step currentStep;
	
	
	@PostConstruct
	void sortActions() {
		actions.sort((a,b) -> a.getName().compareTo(b.getName()));
		for(var a: actions ) {
			System.out.println(a.getName());
		}
	}
	
	void nextAction() {
		currentAction = actions.get(actionIndex);
		actionIndex++;
		if(actionIndex >= actions.size()) {
			actionIndex = 0;
		}
		log.info(currentAction.getName());
		currentStep = currentAction.getFirstStep();
	}
	
	
	@Scheduled(fixedDelay = 500L)
	void clockTick() {
		//log.info("Clock Tick");
		if(clockEnabled) {
			//log.info("Enabled");
			if(currentAction == null) {
				nextAction();
			}
			currentStep = currentAction.doStep(currentStep);
			if(currentStep.isEnded()) {
				nextAction();
			}
		}
	}


	



}
