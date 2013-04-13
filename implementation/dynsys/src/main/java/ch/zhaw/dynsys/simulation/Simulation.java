package ch.zhaw.dynsys.simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import ch.zhaw.dynsys.el.utils.ExpressionUtil;
import ch.zhaw.dynsys.gui.CulturePanel;
import ch.zhaw.dynsys.gui.SettingsPanel;

public class Simulation {
	
	private final SettingsPanel settingsPanel;
	
	private Timer timer = null;
	private List<SimulationListener> simulationListeners = new ArrayList<SimulationListener>();

	public Simulation(SettingsPanel settingsPanel) {
		this.settingsPanel = settingsPanel;
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
			// read culture panels
			List<CulturePanel> culturePanels = settingsPanel.getCulturePanels();
			double[] datasets = ExpressionUtil.evaluateExpressions(culturePanels);
			
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
