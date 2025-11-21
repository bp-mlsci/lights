package com.mlsci.lights.status;

import com.mlsci.lights.client.Color;

import lombok.Getter;


@Getter
public enum ColorOption {
	RED("Red",Color.RED, "red"),
	GREEN("Green", Color.GREEN, "green"),
	BLUE("Blue", Color.BLUE, "blue"),
	YELLOW("Yellow", Color.YELLOW, "yellow"), // R G
	PURPLE("Purple", Color.PURPLE, "purple"), // R B
	CYAN("Cyan", Color.CYAN, "cyan"), // G B
	WARM_WHITE("Warm White", Color.WARM_WHITE, "rgb(239, 235, 216)"),
	COLD_WHITE("Cold White", Color.COLD_WHITE, "rgb(212, 235, 255)"),
	WHITE("White", Color.WHITE, "white");
	
	
	private String label;
	private Color color;
	private String htmlColor;
	private ColorOption(String label, Color color, String htmlColor) {
		this.label = label;
		this.color = color;
		this.htmlColor = htmlColor;
	}
	
}
