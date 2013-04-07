package ch.zhaw.dynsys.gui;

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
		
		ActionListener addLister = new AddListener(p);
		
		JButton add = new JButton("+");
		add.setHorizontalAlignment(SwingConstants.RIGHT);
		add.setBorder(new EmptyBorder(5, 5, 5, 5));
		add.setContentAreaFilled(false);
		add.addActionListener(addLister);
		p.add(add);
		
		Dimension d = new Dimension(300, 100);
		//Dimension d = new CulturePanel().getPreferredSize();
		getVerticalScrollBar().setUnitIncrement(d.height);
		//d.height *= 10;

		viewport.add(p);
		viewport.setPreferredSize(d);
		
		//d.width += 50;
//		setPreferredSize(d);
		
		setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	    setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    
	    addLister.actionPerformed(null);
	    addLister.actionPerformed(null);
	}
	
	private class AddListener implements ActionListener {
		private final static String ABC = "abcdefghijklmnopqrstuvwxyz";
		private Container parent;
		private int i = 0;

		public AddListener(Container parent) {
			super();
			this.parent = parent;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (i >= ABC.length()) {
				return;
			}
			
			parent.add(new CulturePanel(String.valueOf(ABC.toCharArray()[i++])), parent.getComponentCount() - 1);
			parent.revalidate();
		}
		
	}

}
