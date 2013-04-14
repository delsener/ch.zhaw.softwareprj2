package ch.zhaw.dynsys.simulation;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import ch.zhaw.dynsys.el.utils.ExpressionUtil;

public class Simulation implements CultureListener {	
	private Map<String, Culture> cultures = new LinkedHashMap<String, Culture>();
	private Timer timer = null;
	private List<SimulationListener> simulationListeners = new ArrayList<SimulationListener>();

	public Simulation() {
	}
	
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
			synchronized (cultures) {
				ExpressionUtil.evaluateExpressions(cultures.values());
				
				for (SimulationListener l : simulationListeners) {
					l.evolved(cultures.values());
				}
			}
		}
	}


	public void clear() {
		for (SimulationListener l : simulationListeners) {
			l.clear();
		}
	}
	
	@Override
	public void changed(Culture culture) {
		synchronized (cultures) {
			cultures.put(culture.getVariable(), culture);
		}
	}

	@Override
	public void removed(Culture culture) {
		synchronized (cultures) {
			cultures.remove(culture.getVariable());
		}
	}
}
