package com.mlsci.lights.client;

public class RandomColor extends Color {

	@Override
	public Integer getR() { return rnd(); }
	
	
	Integer rnd() {
		var rnd = Math.round(Math.random() * 255);
		return (int) rnd;
	}
	
	@Override
	public Integer getG() { return rnd(); }
	
	@Override 
	public Integer getB() { return rnd(); }
}
