package com.mlsci.lights.client;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RandomColorTest {

	@Test
	void test() {
		var rc = new RandomColor();
		
		assertTrue(diff(rc,rc));
		
		var real = rc.realize();
		
		assertFalse(diff(real,real));
	}

	
	
	
	boolean diff(Color a, Color b) {
		return a.getR().intValue() != b.getR().intValue() ||
				a.getG().intValue() != b.getG().intValue() ||
				a.getB().intValue() != b.getB().intValue();
		
	}
	
}

