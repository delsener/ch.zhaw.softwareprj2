package ch.zhaw.dynsys.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class SettingsPanel extends JScrollPane {
	private static final long serialVersionUID = 1L;

	public SettingsPanel() {
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
		p.add(new CulturePanel());
		p.add(new CulturePanel());
		
		
		JButton add = new JButton("+");
		add.setHorizontalAlignment(SwingConstants.RIGHT);
		add.setBorder(new EmptyBorder(5, 5, 5, 5));
		add.setContentAreaFilled(false);
		add.addActionListener(new AddListener(p));
		p.add(add);
		
		Dimension d = new CulturePanel().getPreferredSize();
		getVerticalScrollBar().setUnitIncrement(d.height);
		d.height *= 10;

		JViewport viewport = getViewport();
		viewport.add(p);
		viewport.setPreferredSize(d);
		
		d.width += 50;
		setPreferredSize(d);
	}
	
	private class AddListener implements ActionListener {
		private Container parent;
		

		public AddListener(Container parent) {
			super();
			this.parent = parent;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			parent.add(new CulturePanel(), parent.getComponentCount() - 2);
			parent.revalidate();
		}
		
	}

}
