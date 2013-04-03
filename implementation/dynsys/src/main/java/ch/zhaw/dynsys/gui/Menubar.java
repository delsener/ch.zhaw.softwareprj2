package ch.zhaw.dynsys.gui;

import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import ch.zhaw.dynsys.simulation.SimulationListener;

public class Menubar extends JMenuBar implements SimulationListener {
	private static final long serialVersionUID = 1L;
	
	// simulations menus
	JMenuItem start = new JMenuItem("Start");
	JMenuItem stop = new JMenuItem("Stop");

	public Menubar(ActionListener startListener, ActionListener stopListener) {
		JMenu file = new JMenu("File");
		add(file);
		
		JMenu view = new JMenu("View");
		add(view);
		
		JMenu simulation = new JMenu("Simulation");
		
		start.addActionListener(startListener);
		simulation.add(start);
		stop.addActionListener(stopListener);
		stop.setEnabled(false);
		simulation.add(stop);
		JMenuItem reset = new JMenuItem("Reset");
		simulation.add(reset);
		add(simulation);
		
		JMenu info = new JMenu("Info");
		add(info);
	}

	@Override
	public void started() {
		start.setEnabled(false);
		stop.setEnabled(true);
	}

	@Override
	public void stoped() {
		start.setEnabled(true);
		stop.setEnabled(false);
	}

	@Override
	public void evolved(double[] values) {}
}
