package ch.zhaw.dynsys.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class Launcher {

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Populations Dynmaik - dynamisches System");
		
		Menubar menubar = new Menubar();
		GraphPanel chartPanel = new GraphPanel();
		SettingsPanel settingsPanel = new SettingsPanel();
		Statusbar statusbar = new Statusbar();

		
		frame.setJMenuBar(menubar);
		frame.add(chartPanel, BorderLayout.CENTER);
		frame.add(settingsPanel, BorderLayout.EAST);
		frame.add(statusbar, BorderLayout.SOUTH);

		frame.pack();
		frame.setVisible(true);
	}

}
