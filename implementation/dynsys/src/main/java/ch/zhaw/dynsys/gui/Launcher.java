package ch.zhaw.dynsys.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JMenuItem;

import ch.zhaw.dynsys.simulation.Simulation;

public class Launcher {

	public static void main(String[] args) {
		
		// setup listeners
		RunningListener runningListener = new RunningListener();
		ClearListener clearListener = new ClearListener();
		Map<String, ActionListener> viewListener = new LinkedHashMap<>();
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
		Simulation simulation = new Simulation();
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

		// show
		frame.pack();
		frame.setVisible(true);
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
