package ch.zhaw.dynsys.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
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
	private static final long serialVersionUID = 1L;

	public CulturePanel(String variable) {
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
		
		JPanel items = new JPanel(new SpringLayout());
		
		JLabel variableLabel = new JLabel("Variable: ", JLabel.TRAILING);
	    items.add(variableLabel);
	    JLabel variableField = new JLabel(variable);
	    variableLabel.setLabelFor(variableField);
	    items.add(variableField);
		
		JLabel nameLabel = new JLabel("Name: ", JLabel.TRAILING);
	    items.add(nameLabel);
	    JTextField nameField = new JTextField(10);
	    nameLabel.setLabelFor(nameField);
	    items.add(nameField);
	    
	    JLabel l = new JLabel("Function: ", JLabel.TRAILING);
	    items.add(l);
	    JTextField textField = new JTextField(10);
	    l.setLabelFor(textField);
	    items.add(textField);
		
		SpringUtilities.makeCompactGrid(items,
                3, 2, //rows, cols
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
			
			parent.revalidate();
			parent.repaint();
		}
		
	}

}
