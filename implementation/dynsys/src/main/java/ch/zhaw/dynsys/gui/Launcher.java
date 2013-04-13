package ch.zhaw.dynsys.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;

import ch.zhaw.dynsys.simulation.Simulation;

public class Launcher {

	public static void main(String[] args) {
		
		// setup listeners
		RunningListener runningListener = new RunningListener();
		ClearListener clearListener = new ClearListener();
		Map<String, ActionListener> viewListener = new LinkedHashMap<String, ActionListener>();
		viewListener.put("50%", new ZoomListener(0.5));
		viewListener.put("100%", new ZoomListener(1));
		viewListener.put("150%", new ZoomListener(1.5));
		viewListener.put("200%", new ZoomListener(2));
		
		// main frame
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Populations Dynmaik - dynamisches System");
		
		// instance gui
		Menubar menubar = new Menubar(viewListener, runningListener, runningListener, clearListener);
		GraphPanel chartPanel = new GraphPanel();
		SettingsPanel settingsPanel = new SettingsPanel();
		Statusbar statusbar = new Statusbar();
		
		// setup listener
		for (Map.Entry<String, ActionListener> entry : viewListener.entrySet()) {
			ZoomListener listener = (ZoomListener)entry.getValue();
			listener.setGraphPanel(chartPanel);
		}
		
		// setup simulation
		Simulation simulation = new Simulation(settingsPanel);
		simulation.addSimulationListener(menubar);
		simulation.addSimulationListener(chartPanel);
		simulation.addSimulationListener(statusbar);
		runningListener.setSimulation(simulation);
		clearListener.setSimulation(simulation);
		
		// add simulation to menubar

		// build gui
		frame.setJMenuBar(menubar);
		frame.add(chartPanel, BorderLayout.CENTER);
		frame.add(settingsPanel, BorderLayout.EAST);
		frame.add(statusbar, BorderLayout.SOUTH);

		// setup example configuration
		setupExampleSettings(settingsPanel);
		
		// show
		frame.pack();
		frame.setVisible(true);
	}
	
	private static void setupExampleSettings(SettingsPanel settingsPanel) {
		List<CulturePanel> culturePanels = settingsPanel.getCulturePanels();
		
		culturePanels.get(0).setVariableDescription("Temperatur (C°)"); // A
		culturePanels.get(0).setVariableValue("20"); // A
		
		culturePanels.get(1).setVariableDescription("Hefemenge"); // B
		culturePanels.get(1).setVariableValue("100"); // B
		
		settingsPanel.addCulturePanel("Zuckermenge", "650"); // C
		
		settingsPanel.addCulturePanel("Einfluss Zucker auf Wachstum", "math:sin(C)"); // D
		settingsPanel.addCulturePanel("Einfluss Temperatur auf Wachstum", "math:cos(A)"); // E
		
		settingsPanel.addCulturePanel("Konstante für Wachstum Hefe", "30"); // F
		settingsPanel.addCulturePanel("Population Hefe", "D * E * B * F"); // G
		
		settingsPanel.addCulturePanel("Konstante für Wachstum Zucker", "5"); // H
		settingsPanel.addCulturePanel("Population Zucker", "G * H"); // I
	}
	
	private static class RunningListener implements ActionListener {
		
		private boolean running = false;
		private Simulation simulation = null;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (running) {
				simulation.stop();
			} else {
				simulation.start();
			}
			
			running = !running;
		}

		public void setSimulation(Simulation simulation) {
			this.simulation = simulation;
		}
	}
	
	private static class ClearListener implements ActionListener {
		private Simulation simulation = null;

		@Override
		public void actionPerformed(ActionEvent e) {
			simulation.clear();
		}

		public void setSimulation(Simulation simulation) {
			this.simulation = simulation;
		}
	}
	
	
	private static class ZoomListener implements ActionListener {
		private GraphPanel graphPanel = null;
		private double factor;
		
		public ZoomListener(double factor) {
			this.factor = factor;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			graphPanel.setZoom(2);
		}

		public void setGraphPanel(GraphPanel graphPanel) {
			this.graphPanel = graphPanel;
		}
	}
}
