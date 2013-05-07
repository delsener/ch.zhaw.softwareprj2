package ch.zhaw.dynsys.gui;

import java.util.List;

import ch.zhaw.dynsys.simulation.Culture;
import ch.zhaw.dynsys.simulation.Simulation;

public class SimulationFactory {

	private List<Culture> cultures;
	private Simulation.Listener listener;
	private static Simulation simulation = null;

	public SimulationFactory(List<Culture> cultures, Simulation.Listener listener) {
		this.cultures = cultures;
		this.listener = listener;
	}
	
	public Simulation getInstance() {
		return new Simulation(cultures, listener);
	}

}
