package com.mlsci.lights.action;

public interface Action {

	Step getFirstStep();

	Step doStep(Step currentStep);

	String getName();

}
