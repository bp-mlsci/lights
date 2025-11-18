package com.mlsci.lights.status;

import lombok.Getter;


@Getter
public enum BrightOption {
	HIGHEST("Highest",254),
	HIGH("High", 200),
	MEDIUM("Medium", 150),
	LOW("Low", 100),
	LOWEST("Lowest", 50);
	
	
	private String label;
	private int brightness;
	private BrightOption(String label, int brightness) {
		this.label = label;
		this.brightness = brightness;
	}
	
}
