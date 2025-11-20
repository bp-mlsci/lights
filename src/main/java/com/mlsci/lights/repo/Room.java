package com.mlsci.lights.repo;

import lombok.Getter;

@Getter
public enum Room {
	MAIN("Main Room"),
	STALLS("Stalls Room");
	
	private String label;
	
	private Room(String label) {
		this.label = label;
	}
}
