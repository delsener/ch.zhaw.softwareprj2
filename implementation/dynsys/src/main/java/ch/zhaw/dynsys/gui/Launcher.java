package ch.zhaw.dynsys.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;

import javax.swing.JFrame;
import javax.swing.JMenuItem;

import ch.zhaw.dynsys.simulation.Simulation;

public class Launcher {

	public static void main(String[] args) {
		
		// setup listeners
		RunningListener runningListener = new RunningListener();
		
		// main frame
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Populations Dynmaik - dynamisches System");
		
		// instance gui
		Menubar menubar = new Menubar(runningListener, runningListener);
		GraphPanel chartPanel = new GraphPanel();
		SettingsPanel settingsPanel = new SettingsPanel();
		Statusbar statusbar = new Statusbar();
		
		// setup simulation
		Simulation simulation = new Simulation();
		simulation.addSimulationListener(menubar);
		simulation.addSimulationListener(chartPanel);
		simulation.addSimulationListener(statusbar);
		runningListener.setSimulation(simulation);
		
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
}
