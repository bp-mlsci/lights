package com.mlsci.lights;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Concurrent implements AutoCloseable {
	
	private ExecutorService executer;
	private List<Future<Boolean>> futures;
	
	public Concurrent() {
		executer = Executors.newVirtualThreadPerTaskExecutor();
		futures = new ArrayList<Future<Boolean>>();
	}

	
	public void submit(Callable<Boolean> callable) {
		futures.add(executer.submit(callable));
		/*
		try {
			callable.call();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
	}
	
	public void fork(Callable<Boolean> callable) {
		submit(callable);
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