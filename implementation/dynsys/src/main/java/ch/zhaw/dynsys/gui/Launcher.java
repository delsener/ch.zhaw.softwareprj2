package ch.zhaw.dynsys.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import ch.zhaw.dynsys.persistence.LoadFromFileListener;
import ch.zhaw.dynsys.persistence.LoadFromPresetsListener;
import ch.zhaw.dynsys.persistence.SaveListener;
import ch.zhaw.dynsys.simulation.Culture;

public class Launcher {

	public static void main(String[] args) {

		// main frame
		final JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Populations Dynmaik - dynamisches System");

		// instance gui
		final GraphPanel graphPanel = new GraphPanel();
		SimulationFactory.setListener(graphPanel);
		final CultureEditor culturesEditor = new CultureEditor();
		final Statusbar statusbar = new Statusbar();

		// setup listeners
		Menubar menubar = new Menubar();

		// menu items
		menubar.addMenuItem("File", "Open..", new LoadFromFileListener(
				culturesEditor));
		menubar.addMenuItem("File", "Import..", new LoadFromPresetsListener(
				culturesEditor));
		menubar.addMenuItem("File", "Save As..", new SaveListener(
				culturesEditor));
		menubar.addMenuItem("View", "All", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				graphPanel.setViewAll(true);
			}
		});
		menubar.addMenuItem("View", "Lastest", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				graphPanel.setViewAll(false);
			}
		});
		menubar.addMenuItem("Simulation", "Run/Resume", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				culturesEditor.setVisible(false);
				SimulationFactory.getInstance().start();
				statusbar.started();
			}
		});
		menubar.addMenuItem("Simulation", "Pause", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SimulationFactory.getInstance().stop();
				culturesEditor.setVisible(true);
				statusbar.stoped();
			}
		});
		menubar.addMenuItem("Simulation", "Stop", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SimulationFactory.getInstance().stop();
				SimulationFactory.newInstance(culturesEditor.getCultures());
				culturesEditor.setVisible(true);
				statusbar.stoped();
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
		frame.add(graphPanel, BorderLayout.CENTER);
		frame.add(culturesEditor, BorderLayout.EAST);
		frame.add(statusbar, BorderLayout.SOUTH);

		culturesEditor.add(new Culture());

		// show
		frame.pack();
		frame.setVisible(true);
	}
}
