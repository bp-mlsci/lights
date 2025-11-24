package com.mlsci.lights;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class NiceConcurrent implements AutoCloseable {
	
	private ExecutorService executer;
	private List<Future<Boolean>> futures;
	private long lastDelay = 0L;
	private long delayIncrement;
	
	
	
	//each fork sleeps a little more before starting to be nice to resources
	
	public NiceConcurrent(long delayIncrement) {
		executer = Executors.newVirtualThreadPerTaskExecutor();
		futures = new ArrayList<Future<Boolean>>();
		this.delayIncrement = delayIncrement;
	}

	
	public void fork(Callable<Boolean> callable) {
		lastDelay += delayIncrement;
		var delay = lastDelay;
		futures.add(executer.submit(() -> {
				Thread.sleep(delay);
				return callable.call();
			}
		));
	}
	
	
	public void join() throws Exception {	
		for(var future : futures) {
			try {
				var ok = future.get();
				if(!Boolean.TRUE.equals(ok)) {
					throw new Exception("Did not return OK!");
				}
			} catch(Exception ex) {
				throw ex;
			}
		}
	}


	@Override
	public void close() throws Exception {
		executer.close();
	}
	
}