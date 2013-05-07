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
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.apache.commons.lang3.StringUtils;

import ch.zhaw.dynsys.simulation.Culture;

public class CulturePropertyEditor extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private Culture culture;
	
	public CulturePropertyEditor() {
		this(new Culture());
	}

	public CulturePropertyEditor(final Culture culture) {
		this.culture = culture;
		
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
		add(close, BorderLayout.NORTH);
		
		JPanel items = new JPanel(new SpringLayout());
		
	    // name label
		items.add(new JValidatedField(50, "Rabbit", culture.getName(), new JValidatedField.Validator() {		
			@Override
			public boolean validate(String value) {
				if (StringUtils.isBlank(value)) {
					return false;
				}
				
				culture.setName(value);	
				return true;
			}
		}));
		
	    // quantity
	    items.add(new JValidatedField(50, "cos(t)", culture.getExpression(), new JValidatedField.Validator() {		
			@Override
			public boolean validate(String value) {
				culture.setExpression(value);	
				return true;
			}
		}));
	    
	    // expression
	    items.add(new JValidatedField(50, "120", Double.isNaN(culture.getPopulation()) ? null : String.valueOf(culture.getPopulation()), new JValidatedField.Validator() {		
			@Override
			public boolean validate(String value) {
				try {
					culture.setPopulation(Double.parseDouble(value));	
					return true;
				} catch (NumberFormatException e) {
					return false;
				}
			}
		}));
	    
	    add(items);
		
		SpringUtilities.makeCompactGrid(items,
                3, 1, //rows, cols
                6, 6,        //initX, initY
                6, 6);		
	}
	
	public void addCloseListener(ActionListener l) {
		((JButton)getComponent(0)).addActionListener(new CloseListenerAdapter(l));
	}
	
	
	public Culture getCulture() {
		return culture;
	}	
	
	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		
		getComponent(0).setEnabled(enabled);
		
		for (Component component : ((Container)getComponent(1)).getComponents()) {
			component.setEnabled(enabled);
		}
	}


	private class CloseListenerAdapter implements ActionListener {
		
		private ActionListener listener;
		
		public CloseListenerAdapter(ActionListener listener) {
			super();
			this.listener = listener;
		}


		@Override
		public void actionPerformed(ActionEvent e) {
			JComponent button = (JComponent)e.getSource();
			e.setSource(button.getParent());
			
			listener.actionPerformed(e);
		}
		
	}

}
