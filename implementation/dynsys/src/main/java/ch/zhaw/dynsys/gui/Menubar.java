package ch.zhaw.dynsys.gui;

import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Menubar extends JMenuBar {
	private static final long serialVersionUID = 1L;	
	
	public void addMenuItem(String menuName, String itemName, ActionListener listener) {
		JMenu menu = null;
		for (int i = 0; i < getMenuCount(); i++) {
			JMenu m = getMenu(i);
			if (menuName.equals(m.getText())) {
				menu = m;
				break;
			}
		}
		
		if (menu == null) {
			menu = new JMenu(menuName);
			add(menu);
		}
		
		JMenuItem item = new JMenuItem(itemName);
		item.addActionListener(listener);
		menu.add(item);
	}
}
