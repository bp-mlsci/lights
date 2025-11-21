package com.mlsci.lights.status;

import lombok.Getter;


@Getter
public enum BrightOption {
	HIGHEST("Full",254),
	HIGH("High", 200),
	MEDIUM("Mid", 150),
	LOW("Low", 100),
	LOWEST("Dim", 50);
	
	
	private String label;
	private int brightness;
	private BrightOption(String label, int brightness) {
		this.label = label;
		this.brightness = brightness;
	}
	
}
