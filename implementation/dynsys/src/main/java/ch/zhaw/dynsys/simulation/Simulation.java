package ch.zhaw.dynsys.simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Simulation {
	
	private Timer timer = null;
	private List<SimulationListener> simulationListeners = new ArrayList<SimulationListener>();

	public void start() {
		for (SimulationListener l : simulationListeners) {
			l.started();
		}
		
		timer = new Timer();
		timer.scheduleAtFixedRate(new Task(simulationListeners), 0, 1000);
	}

	public void stop() {
		timer.cancel();
		
		for (SimulationListener l : simulationListeners) {
			l.stoped();
		}
	}
	
	public void addSimulationListener(SimulationListener listener) {
		simulationListeners.add(listener);
	}
	
	public void removeSimulationListener(SimulationListener listener) {
		simulationListeners.remove(listener);
	}
	
	
	private class Task extends TimerTask {
		private List<SimulationListener> simulationListeners = new ArrayList<SimulationListener>();
		
		public Task(List<SimulationListener> simulationListeners) {
			this.simulationListeners = simulationListeners;
		}

		@Override
		public void run() {
			double[] datasets = new double[5];
			
			Random random = new Random();
			for (int i=0; i<datasets.length; i++) {
				datasets[i] = random.nextDouble();
			}

			for (SimulationListener l : simulationListeners) {
				l.evolved(datasets);
			}
		}
	}


	public void clear() {
		for (SimulationListener l : simulationListeners) {
			l.clear();
		}
	}
}
