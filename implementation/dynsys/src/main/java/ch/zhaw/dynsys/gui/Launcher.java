package ch.zhaw.dynsys.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JFrame;

import ch.zhaw.dynsys.persistence.LoadFromFileListener;
import ch.zhaw.dynsys.persistence.LoadFromPresetsListener;
import ch.zhaw.dynsys.persistence.SaveListener;
import ch.zhaw.dynsys.simulation.Simulation;

public class Launcher {

	public static void main(String[] args) {

		// main frame
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Populations Dynmaik - dynamisches System");

		// instance gui
		GraphPanel chartPanel = new GraphPanel();
		SettingsPanel settingsPanel = new SettingsPanel();
		Statusbar statusbar = new Statusbar();

		// setup listeners
		RunningListener runningListener = new RunningListener();
		ClearListener clearListener = new ClearListener();
		Map<String, ActionListener> viewListener = new LinkedHashMap<String, ActionListener>();
		viewListener.put("View all", new ViewListener(true));
		viewListener.put("View lastest", new ViewListener(false));
		SaveListener saveListener = new SaveListener(settingsPanel);
		LoadFromFileListener loadFileListener = new LoadFromFileListener(
				settingsPanel);
		LoadFromPresetsListener loadPresetsListener = new LoadFromPresetsListener(
				settingsPanel);

		Menubar menubar = new Menubar(viewListener, runningListener,
				runningListener, clearListener, saveListener, loadFileListener,
				loadPresetsListener);

		// setup listener
		for (Map.Entry<String, ActionListener> entry : viewListener.entrySet()) {
			ActionListener actionListener = entry.getValue();
			
			if (actionListener instanceof ViewListener) {
				ViewListener listener = (ViewListener)actionListener;
				listener.setGraphPanel(chartPanel);
			}
		}

		// setup simulation
		Simulation simulation = new Simulation();
		simulation.addSimulationListener(menubar);
		simulation.addSimulationListener(chartPanel);
		simulation.addSimulationListener(statusbar);
		runningListener.setSimulation(simulation);
		clearListener.setSimulation(simulation);
		settingsPanel.addCultureListener(simulation);

		// add simulation to menubar
		// build gui
		frame.setJMenuBar(menubar);
		frame.add(chartPanel, BorderLayout.CENTER);
		frame.add(settingsPanel, BorderLayout.EAST);
		frame.add(statusbar, BorderLayout.SOUTH);

		settingsPanel.addCulturePanel(null);

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
	
	
	private static class ViewListener implements ActionListener {
		private GraphPanel graphPanel = null;
		private boolean viewAll;



		public ViewListener(boolean viewAll) {
			this.viewAll = viewAll;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			graphPanel.setViewAll(viewAll);
		}

		public void setGraphPanel(GraphPanel graphPanel) {
			this.graphPanel = graphPanel;
		}
	}
}
