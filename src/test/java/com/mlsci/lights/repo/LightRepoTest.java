package com.mlsci.lights.repo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.mlsci.lights.client.BulbData;

class LightRepoTest {

	
	LightRepo lightRepo = new LightRepo();
	
	@Test
	void testGetChase() {
		var lights = lightRepo.getChase();
		assertNotNull(lights);
	}
	
	
	
	Light l(String ip, int row, int col) {
		var l = new Light();
		l.setIp(ip);
		l.setRow(row);
		l.setCol(col);
		return l;
		
	}
	
	@Test
	void chase3() {
		var r = new LightRepo();
		var l1 = l("l1",6,3);
		r.add(l1);
		var l2 = l("l2", 0,0);
		r.add(l2);
		var l3 = l("l3", 4,1);
		r.add(l3);
		
		var lights = r.getChase();
		assertEquals(3, lights.size());
		for(var l : lights) {
			System.out.println(l.getRow() + " " + l.getCol() + " " + l.getIp());
		}
		assertEquals(l2, lights.get(0));
		
	}
	
	
	@Test
	void testAssignLoc() {
		var l = new Light();
		lightRepo.assignLocation(l);
		assertEquals(-1, l.getRow());
		assertEquals(-1, l.getCol());
		
		l.setBulbData(new BulbData());
		l.getBulbData().setTitle("kauf bulb " + LightRepo._080AAC);
		lightRepo.assignLocation(l);
		assertTrue(l.getCol() >= 0);
		assertTrue(l.getRow() >= 0);
		
		
	}
	
	
	@Test
	void chase4() {
		var r = new LightRepo();
		for(int row = 0; row < 7; row++) {
				var l1 = l("l "+row+" 0",row,0);
				r.add(l1);
				var l2 = l("l "+row+" 5", row,5);
				r.add(l2);
		}
		var lights = r.getChase();
		for(var l : lights) {
			System.out.println(l.getRow() + " " + l.getCol() + " " + l.getIp());
		}
		
	}

}
