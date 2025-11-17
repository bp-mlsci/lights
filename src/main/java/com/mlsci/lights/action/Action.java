package com.mlsci.lights.action;

import com.mlsci.lights.repo.Room;

public interface Action {

	Step getFirstStep();

	Step doStep(Step currentStep);

	String getName();

	Room getRoom();
	
}
