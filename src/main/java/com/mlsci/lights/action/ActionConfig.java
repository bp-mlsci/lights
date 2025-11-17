package com.mlsci.lights.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mlsci.lights.client.Color;
import com.mlsci.lights.client.LightClient;
import com.mlsci.lights.repo.LightRepo;
import com.mlsci.lights.repo.Room;

@Configuration
public class ActionConfig {

	@Autowired LightClient lightClient;
	@Autowired LightRepo lightRepo;
	
	
	
	@Bean 
	Action days1() { return days("A Days1"); }
	
	@Bean 
	Action rainbow1() { return rainbow("B Rainbow1");}


	@Bean 
	Action christmas1() { return christmas("C Christmas1"); }
	
	
	//@Bean
	Action chase1() { return new Chase(lightClient, lightRepo, "D Chase 1",
				List.of(Color.BLUE, Color.RED, Color.BLACK)
				, 1, Room.MAIN);
	}
	
	@Bean 
	Action days2() { return days("E Days2"); }
	
	
	@Bean 
	Action christmas2() { return christmas("F Christmas2"); }

	
	
	//@Bean
	Action chase2() {
		return new Chase(lightClient, lightRepo, "G Chase 2",
				List.of(Color.INDIGO, Color.ORANGE, Color.BLACK)
				, -1, Room.MAIN);
	}

	
	@Bean 
	Action rainbow2() { return rainbow("H Rainbow2");}

	
	
	Action christmas(String name) {
		var l = new Looper(lightClient, lightRepo, name, Room.MAIN);
				
		for(int i = 1; i < 4; i++ ) {
			l.add(Color.RED, 20, i * 50);
		    l.add(Color.GREEN, 20, i * 50);
		}
		return l;
	}
	
	
	
	Action days(String name) { 
		var l = new Looper(lightClient, lightRepo, name, Room.MAIN);
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
	
	
	
	Action rainbow(String name) { 
		var l = new Looper(lightClient, lightRepo, name, Room.MAIN);
		for(int i = 1; i < 5; i++ ) {
			for(var color : Color.COLORS) {
				l.add(color, 10, i * 40);
			}
		}
		return l;
	}

	
}
