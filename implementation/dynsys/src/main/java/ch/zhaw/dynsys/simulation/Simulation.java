package ch.zhaw.dynsys.simulation;

import java.util.Collection;
import java.util.List;

import org.apache.commons.jexl2.JexlException;

import ch.zhaw.dynsys.el.utils.ExpressionUtil;

public class Simulation {	
	private List<Culture> cultures;
	private Object lock = new Object();
	private boolean running;
	private Listener listener;
	private long start = 0;
	private Thread thread = null;
	
	public Simulation(List<Culture> cultures, Listener listener) {
		this.cultures = cultures;
		this.listener = listener;
	}
	
	public void start() {
		if (cultures.size() == 0) {
			return;
		}
		
		for (Culture culture : cultures) {
			if (!culture.isValid()) {
				return;
			}
		}
		
		synchronized (lock) {
			if (running) {
				return;
			}
		}
		
		running = true;

		
		start = System.currentTimeMillis();
		
		thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				boolean isRunning = true;
				
				
				while (isRunning) {
					try {
						long now = System.currentTimeMillis();
						long time = now - start;
						ExpressionUtil.evaluateExpressions(cultures, time);
						
						listener.evolved(cultures, time);
					} catch (JexlException e) {
						// error in evaluation, stop simulation
						stop();
					}
					
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// do nothing
					}
					
					synchronized (lock) {
						isRunning = running;
					}
				}
			}
		});
		
		thread.start();
	}

	public void stop() {		
		synchronized (lock) {
			if (!running) {
				return;
			}
			
			running = false;
		}
		
		try {
			thread.join();
		} catch (InterruptedException e) {
		}
	}
	
	
	public boolean isRunning() {
		synchronized (lock) {
			return running;
		}
	}
	
	
	public static interface Listener {
		public void evolved(Collection<Culture> cultures, long time);
	}
}
