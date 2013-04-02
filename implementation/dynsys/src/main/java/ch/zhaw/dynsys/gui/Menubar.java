package ch.zhaw.dynsys.gui;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

public class Menubar extends JMenuBar {
	private static final long serialVersionUID = 1L;

	public Menubar() {
		JMenu file = new JMenu("File");
		add(file);
	}

}
