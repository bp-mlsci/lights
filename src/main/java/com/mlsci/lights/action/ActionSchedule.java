package com.mlsci.lights.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.mlsci.lights.repo.Room;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class ActionSchedule {
	private boolean clockEnabled = true;
	private final List<Action> actions;
	private Map<Room,RoomAction> roomActions;
	
	@PostConstruct
	void sortActions() {
		actions.sort((a,b) -> a.getName().compareTo(b.getName()));
		for(var a: actions ) {
			System.out.println(a.getName());
		}
		roomActions = new HashMap<Room,RoomAction>();
		for(var action : actions) {
			var ra = roomActions.get(action.getRoom());
			if(ra == null) {
				ra = new RoomAction();
				ra.setRoom(action.getRoom());
				ra.setActions(new ArrayList<Action>());
				roomActions.put(action.getRoom(), ra);
			}
			ra.getActions().add(action);
		}
		System.out.println("There are " + roomActions.size() + " room actions");
	}
	
	
	
	@Scheduled(fixedDelay = 500L)
	void clockTick() {
		//log.info("Clock Tick");
		if(clockEnabled) {
			//log.info("Enabled");
			for(var ra : roomActions.values()) {
				ra.clockTick();
			}

		}
	}



	public void pause() {
		clockEnabled = false;
	}


	public void resume() {
		clockEnabled = true;
	}





}
