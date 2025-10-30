package com.mlsci.lights.client;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LightStatus {
	private String id;
	private String name;
	private String state;
	private String color_mode;
	private Integer brightness;
	private Color color;
	private Integer white_value;
	private Integer color_temp;
}
