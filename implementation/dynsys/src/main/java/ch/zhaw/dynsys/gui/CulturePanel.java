package ch.zhaw.dynsys.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class CulturePanel extends JPanel {

	public CulturePanel() {
		setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
		setMaximumSize(new Dimension(300, 100));
		setAlignmentX(Component.LEFT_ALIGNMENT);
		setAlignmentY(Component.TOP_ALIGNMENT);
		
		setLayout(new BorderLayout());
		
		// close button
		JButton close = new JButton("x");
		close.setHorizontalAlignment(SwingConstants.RIGHT);
		close.setBorder(new EmptyBorder(0, 0, 0, 5));
		close.setPreferredSize(new Dimension(10, 10));
		close.setContentAreaFilled(false);
		close.addActionListener(new CloseListener(this));
		add(close, BorderLayout.NORTH);
		
		JPanel items = new JPanel(new SpringLayout()) ;	
		
		
		String[] labels = {"Name: ", "Variable: ", "Function: " };
		int numPairs = labels.length;


		for (int i = 0; i < numPairs; i++) {
		    JLabel l = new JLabel(labels[i], JLabel.TRAILING);
		    items.add(l);
		    JTextField textField = new JTextField(10);
		    l.setLabelFor(textField);
		    items.add(textField);
		}
		
		SpringUtilities.makeCompactGrid(items,
                numPairs, 2, //rows, cols
                6, 6,        //initX, initY
                6, 6); 
		
		add(items);
	}
	
	
	private class CloseListener implements ActionListener {
		private Component component;
		

		public CloseListener(Component component) {
			super();
			this.component = component;
		}

		@Override
		public void actionPerformed(ActionEvent e) {			
			Container parent = component.getParent();			
			parent.remove(component);
			
			// if last one, add new one
			if (parent.getComponentCount() == 1) {
				parent.add(new CulturePanel(), 0);
			}
			
			parent.revalidate();
			parent.repaint();
		}
		
	}

}
