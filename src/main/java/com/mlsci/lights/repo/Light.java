package com.mlsci.lights.repo;

import com.mlsci.lights.client.BulbData;
import com.mlsci.lights.client.LightStatus;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Light {
	private Bulb bulb;
	private LightStatus lightStatus;
	private BulbData bulbData;
	private String lastCommand;
	private String lastResult;
}
