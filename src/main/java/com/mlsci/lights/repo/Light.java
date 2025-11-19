package com.mlsci.lights.repo;

import java.time.LocalDateTime;

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
	private History[] history = new History[10];
	private LightState lightState = LightState.NEW;
	private int brightness;
	private int colorTemp;
	private int red;
	private int green;
	private int blue;
	
	
	public void addHistory(String url, String result) {
		var hist = new History(url, result, LocalDateTime.now());
		for(int i = history.length -1 ; i >0; i--) {
			history[i] = history[i-1];
		}
		history[0] = hist;
		
	}
	
	public int getErrorCount() {
		var count = 0;
		for(var h : history) {
			if(h != null) {
				if(! "200 OK".equals(h.result())) {
					count++;
				}
			}
		}
		return count;
	}
	
	
}
