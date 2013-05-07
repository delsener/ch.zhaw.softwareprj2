package ch.zhaw.dynsys.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import ch.zhaw.dynsys.persistence.LoadFromFileListener;
import ch.zhaw.dynsys.persistence.LoadFromPresetsListener;
import ch.zhaw.dynsys.persistence.SaveListener;
import ch.zhaw.dynsys.simulation.Culture;
import ch.zhaw.dynsys.simulation.Simulation;

public class Launcher {

	public static void main(String[] args) {

		// main frame
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Populations Dynmaik - dynamisches System");

		// instance gui
		final GraphPanel chartPanel = new GraphPanel();
		final CultureEditor culturesEditor = new CultureEditor();
		Statusbar statusbar = new Statusbar();
		SimulationFactory simulationFactory = new SimulationFactory(culturesEditor.getCultures(), chartPanel);
		final List<Simulation> simulations = new ArrayList<Simulation>();

		// setup listeners
		Menubar menubar = new Menubar();
		
		// menu items
		menubar.addMenuItem("File", "Open..", new LoadFromFileListener(culturesEditor));
		menubar.addMenuItem("File", "Import..", new LoadFromPresetsListener(culturesEditor));
		menubar.addMenuItem("File", "Save As..", new SaveListener(culturesEditor));
		menubar.addMenuItem("View", "All", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				chartPanel.setViewAll(true);
			}
		});
		menubar.addMenuItem("View", "Lastest", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				chartPanel.setViewAll(false);
			}
		});
		menubar.addMenuItem("Simulation", "Run/Resume", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (simulations.size() > 0) {
					if (simulations.get(0).isRunning()) {
						return;
					}
					simulations.get(0).start();
				} else {
					Simulation simulation = new Simulation(culturesEditor.getCultures(), chartPanel);
					simulations.add(simulation);
					simulation.start();
				}
			}
		});
		menubar.addMenuItem("Simulation", "Pause", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (simulations.size() > 0) {
					for (Simulation simulation : simulations) {
						simulation.stop();
					}
				}
			}
		});
		menubar.addMenuItem("Simulation", "Stop", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (simulations.size() > 0) {
					for (Simulation simulation : simulations) {
						simulation.stop();
					}
					simulations.clear();
				}
			}
		});
		menubar.addMenuItem("Info", "About", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String title = "About this software";
				String message = "This software was written for Zurich University of Applied Siences (ZHAW)\n at depature School of Engineering in collaboration with Albert Heuberger\n and Syrus Mozafar.\n\nAuthors:\nDavid Elsener\nRoger Knecht";
				JOptionPane.showMessageDialog(null, message, title,
						JOptionPane.INFORMATION_MESSAGE);
			}
		});

		// add simulation to menubar
		frame.setJMenuBar(menubar);
		frame.add(chartPanel, BorderLayout.CENTER);
		frame.add(culturesEditor, BorderLayout.EAST);
		frame.add(statusbar, BorderLayout.SOUTH);

		culturesEditor.add(new Culture());

		// show
		frame.pack();
		frame.setVisible(true);
	}
}
