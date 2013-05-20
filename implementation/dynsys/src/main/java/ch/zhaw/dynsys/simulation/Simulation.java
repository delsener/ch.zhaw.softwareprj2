package ch.zhaw.dynsys.simulation;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.commons.jexl2.JexlException;

import ch.zhaw.dynsys.el.utils.ExpressionUtil;

public class Simulation {
	private List<Culture> cultures;
	private Object lock = new Object();
	private boolean running;
	private Listener listener;
	private Thread thread = null;
	private final double ITERATION_STEP = 0.01;
	private int iteration = 0;

	public Simulation(List<Culture> cultures, Listener listener) {
		this.cultures = new ArrayList<Culture>();
		this.listener = listener;
		
		for (Culture culture : cultures) {
			if (culture.isValid()) {
				this.cultures.add(culture);
				culture.setValue(culture.getPopulation());
			}
		}
		listener.start(this.cultures);
	}

	public boolean start() {
		if (cultures.size() == 0) {
			return false;
		}
		
		try {
			ExpressionUtil.evaluateExpressions(this.cultures, 1);
		} catch (Exception e) {
			String message = e.getMessage().replace(";", ";\n");
			JOptionPane.showMessageDialog(null, message, "Expression Language Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		running = true;

		thread = new Thread(new Runnable() {

			@Override
			public void run() {
				boolean isRunning = true;

				while (isRunning) {
					try {
						double[] y = new double[cultures.size()];
						for (int i = 0; i < y.length; i++) {
							y[i] = cultures.get(i).getValue();
						}
						
						listener.updated(iteration*ITERATION_STEP, y);
						iteration++;
						ExpressionUtil.evaluateExpressions(cultures, ITERATION_STEP);
					} catch (JexlException e) {
						// error in evaluation, stop simulation
						stop();
					}

					try {
						Thread.sleep(20);
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
		return true;
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
		public void updated(double x, double[] y);
	}

	public List<Culture> getCultures() {
		return cultures;
	}

	
}
