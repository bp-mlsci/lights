package com.mlsci.lights.repo;

import lombok.Getter;

@Getter
public enum LightMode {
	AUTO("automatic"),
	MANUAL("manual");
	
	private String label;
	private LightMode(String label) {
		this.label = label;
	}
}
