package com.mlsci.lights.discover;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class DiscoverSchedule {
	private final Discover discover;
	
	@Scheduled(fixedDelay = 10_000L)
	void doDiscover() {
		discover.search();
	}
}
