package ch.zhaw.dynsys.simulation;

import java.util.List;

import org.apache.commons.jexl2.JexlException;

import ch.zhaw.dynsys.el.utils.ExpressionUtil;

public class Simulation {
	private List<Culture> cultures;
	private Object lock = new Object();
	private boolean running;
	private Listener listener;
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
			culture.setValue(culture.getPopulation());
		}

		synchronized (lock) {
			if (running) {
				return;
			}
		}
		
		final double iteration = 0.01;
		if (thread == null) {		
			listener.start(cultures);
		}

		running = true;

		thread = new Thread(new Runnable() {

			@Override
			public void run() {
				boolean isRunning = true;

				while (isRunning) {
					try {
						ExpressionUtil.evaluateExpressions(cultures, iteration);

						listener.updated();
					} catch (JexlException e) {
						// error in evaluation, stop simulation
						stop();
					}

					try {
						Thread.sleep(60);
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
		public void start(List<Culture> cultures);
		public void updated();
	}

	public List<Culture> getCultures() {
		return cultures;
	}

	
}
