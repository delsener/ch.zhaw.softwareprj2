package ch.zhaw.dynsys.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import ch.zhaw.dynsys.simulation.SimulationListener;

public class Menubar extends JMenuBar implements SimulationListener {
	private static final long serialVersionUID = 1L;
	
	// simulations menus
	private JMenuItem start = new JMenuItem("Start");
	private JMenuItem stop = new JMenuItem("Stop");
	private JMenuItem reset = new JMenuItem("Clear History");

	public Menubar(Map<String, ActionListener> viewListener, ActionListener startListener, ActionListener stopListener, ActionListener resetListener) {
		JMenu file = new JMenu("File");
		add(file);
		
		JMenu view = new JMenu("View");
		for (Map.Entry<String, ActionListener> entry : viewListener.entrySet()) {
			JMenuItem item = new JMenuItem(entry.getKey());
			item.addActionListener(entry.getValue());
			view.add(item);
		}
		add(view);
		
		JMenu simulation = new JMenu("Simulation");
		
		start.addActionListener(startListener);
		simulation.add(start);
		stop.addActionListener(stopListener);
		stop.setEnabled(false);
		simulation.add(stop);
		reset.addActionListener(resetListener);
		reset.setEnabled(false);
		simulation.add(reset);
		add(simulation);
		
		JMenu info = new JMenu("Info");
		JMenuItem about = new JMenuItem("About");
		about.addActionListener(new AboutListener());
		info.add(about);
		add(info);
	}

	@Override
	public void started() {
		start.setEnabled(false);
		stop.setEnabled(true);
		reset.setEnabled(false);
	}

	@Override
	public void stoped() {
		start.setEnabled(true);
		stop.setEnabled(false);
		reset.setEnabled(true);
	}

	@Override
	public void evolved(double[] values) {}
	
	
	@Override
	public void clear() {
		reset.setEnabled(false);
	}
	
	private class AboutListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String title = "About this software";
			String message = "This software was written for Zurich University of Applied Siences (ZHAW)\n at depature School of Engineering in collaboration with Albert Heuberger\n and Syrus Mozafar.\n\nAuthors:\nDavid Elsener\nRoger Knecht";
			JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);			
		}
		
	}
}
