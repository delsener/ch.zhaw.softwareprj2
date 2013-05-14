package ch.zhaw.dynsys.gui;

import java.util.ArrayList;
import java.util.List;

import ch.zhaw.dynsys.simulation.Culture;
import ch.zhaw.dynsys.simulation.Simulation;

public abstract class SimulationFactory {

	private static Simulation.Listener listener = null;
	private static Simulation simulation;
	
	public static Simulation getInstance() {
		if (simulation == null) {
			simulation = newInstance(new ArrayList<Culture>());
		}
		return simulation;
	}
	
	public static Simulation newInstance(List<Culture> culture) {
		if (simulation != null) {
			simulation.stop();
		}
		
		simulation = new Simulation(culture, listener);
		return simulation;
	}
	
	public static void setListener(Simulation.Listener listener) {
		SimulationFactory.listener = listener; 
	}

}
