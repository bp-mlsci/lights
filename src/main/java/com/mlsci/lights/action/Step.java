package com.mlsci.lights.action;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Step {
	private int index = 0;
	private long untilTimeMillis;
	private boolean ended = false;
	private int count =0;
	
}
