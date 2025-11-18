package com.mlsci.lights.status;

import com.mlsci.lights.client.Color;

import lombok.Getter;


@Getter
public enum ColorOption {
	RED("Red",Color.RED),
	GREEN("Green", Color.GREEN),
	BLUE("Blue", Color.BLUE),
	YELLOW("Yellow", Color.YELLOW), // R G
	PURPLE("Purple", Color.PURPLE), // R B
	CYAN("Cyan", Color.CYAN), // G B
	WARM_WHITE("Warm White", Color.WARM_WHITE),
	COLD_WHITE("Cold White", Color.COLD_WHITE),
	WHITE("White", Color.WHITE);
	
	
	private String label;
	private Color color;
	private ColorOption(String label, Color color) {
		this.label = label;
		this.color = color;
	}
	
}
