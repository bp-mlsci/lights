package com.mlsci.lights.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mlsci.lights.client.Color;
import com.mlsci.lights.client.LightClient;
import com.mlsci.lights.repo.LightRepo;
import com.mlsci.lights.repo.Room;
import static com.mlsci.lights.repo.Room.*;



@Configuration
public class ActionConfig {

	@Autowired LightClient lightClient;
	@Autowired LightRepo lightRepo;
	
	
	
	@Bean
	Action randomMain1() { return new RandomAction(lightClient, lightRepo, "110 Random Main1", MAIN, 9); }
	
	
	@Bean
	Action randomStall1() { return new RandomAction(lightClient, lightRepo, "210 Random Stalls1", STALLS, 5); }
	
	
	@Bean
	Action brightColorsStalls1() { return brightColors("220 Bright Colors Stalls", STALLS); }
	
	@Bean 
	Action days1() { return days("120 Days1", MAIN); }
	
	@Bean 
	Action rainbow1() { return rainbow("130 Rainbow1", MAIN);}


	@Bean 
	Action christmas1() { return christmas("140 Christmas1", MAIN); }
	
	
	//@Bean
	Action chase1() { return new Chase(lightClient, lightRepo, "150 Chase 1",
				List.of(Color.BLUE, Color.RED, Color.BLACK)
				, 1, MAIN);
	}
	
	@Bean 
	Action days2() { return days("160 Days2", MAIN); }
	
	
	@Bean 
	Action christmas2() { return christmas("170 Christmas2", MAIN); }

	
	
	//@Bean
	Action chase2() {
		return new Chase(lightClient, lightRepo, "180 Chase 2",
				List.of(Color.INDIGO, Color.ORANGE, Color.BLACK)
				, -1, MAIN);
	}

	
	@Bean 
	Action rainbow2() { return rainbow("190 Rainbow2", MAIN);}

	
	
	Action christmas(String name, Room room) {
		var l = new Looper(lightClient, lightRepo, name, room);
				
		for(int i = 1; i < 4; i++ ) {
			l.add(Color.RED, 20, i * 50);
		    l.add(Color.GREEN, 20, i * 50);
		}
		return l;
	}
	
	
	
	Action days(String name, Room room) { 
		var l = new Looper(lightClient, lightRepo, name, room);
		for(int i = 1; i < 3; i++ ) {
			l.add(Color.INDIGO, 15, 200);
		    l.add(Color.SUNRISE_YELLOW, 20, 200);
		    l.add(Color.HIGH_NOON, 60, 200);
		    l.add(Color.HIGH_NOON, 60, 250);
		    l.add(Color.HIGH_NOON, 60, 200);
		    l.add(Color.SUNSET_ORANGE, 20, 200);
		    l.add(Color.INDIGO, 15, 200);
		    l.add(Color.BLACK, 30, 20);
		}
		return l;
	}
	
	
	Action brightColors(String name, Room room) { 
		var l = new Looper(lightClient, lightRepo, name, room);
		for(int i = 1; i < 4; i++ ) {
			l.add(Color.RED, 20, 60*i);
		    l.add(Color.GREEN, 20, 60*i);
		    l.add(Color.HIGH_NOON, 60, 200);
		    l.add(Color.HIGH_NOON, 60, 250);
		    l.add(Color.HIGH_NOON, 60, 200);
			l.add(Color.RED, 20, 220);
		    l.add(Color.GREEN, 20, 220);
		}
		return l;
	}
	
	
	Action rainbow(String name, Room room) { 
		var l = new Looper(lightClient, lightRepo, name, room);
		for(int i = 1; i < 5; i++ ) {
			for(var color : Color.COLORS) {
				l.add(color, 10, i * 40);
			}
		}
		return l;
	}

	
}
