package ch.zhaw.dynsys.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.apache.commons.lang3.StringUtils;

import ch.zhaw.dynsys.simulation.Culture;

public class CulturePanel extends JPanel implements FocusListener {
	
	private static final long serialVersionUID = 1L;
	private static final Font FIELD_EMPTY_FONT = new Font("Arial", Font.ITALIC, 12);
	private static final Font FIELD_FILLED_FONT = new Font("Arial", Font.PLAIN, 12);
	
	private Culture culture = new Culture();
	private JTextField nameField;
	private JTextField expressionField;
	private JTextField quantityField;
	
	private FocusListener focusListener;

	public CulturePanel(Culture culture, FocusListener focusListener, ActionListener onClose) {
		this.focusListener = focusListener;
		
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
		close.addActionListener(onClose);
		add(close, BorderLayout.NORTH);
		
		JPanel items = new JPanel(new SpringLayout());
		
	    // name label
	    nameField = new JTextField(50);
	    nameField.addFocusListener(this);
	    items.add(nameField);
	    
	    // expression
	    expressionField = new JTextField(50);
	    expressionField.addFocusListener(this);
	    items.add(expressionField);
	    
	    // quantity
	    quantityField = new JTextField(50);
	    quantityField.addFocusListener(this);
	    items.add(quantityField);
	    
	    add(items);
	    
	    if (culture != null) {
	    	this.culture = culture;
	    	nameField.setText(culture.getName());
	    	expressionField.setText(culture.getVariable() + " = " + culture.getExpression());
	    	quantityField.setText(String.valueOf(culture.getPopulation()));
	    	FocusEvent e = new FocusEvent(nameField, 0);
	    	focusListener.focusLost(e);
	    } else {
	    	this.culture = new Culture();
	    	resetNameField();
	    	resetExpressionField();
	    	resetQuantityField();
	    }
		
		SpringUtilities.makeCompactGrid(items,
                3, 1, //rows, cols
                6, 6,        //initX, initY
                6, 6);
	}
	
	private void resetNameField() {
		// name label
	    nameField.setText("Culture");
	    nameField.setFont(FIELD_EMPTY_FONT);
	    nameField.setForeground(Color.GRAY);
	}
	    
	private void resetExpressionField() {
	    // expression
	    expressionField.setText("y = cos(t)");
	    expressionField.setFont(FIELD_EMPTY_FONT);
	    expressionField.setForeground(Color.GRAY);
	}
	    
	private void resetQuantityField() {
	    // quantity
	    quantityField.setText("120");
	    quantityField.setFont(FIELD_EMPTY_FONT);
	    quantityField.setForeground(Color.GRAY);
	}

	public Culture getCulture() {
		return culture;
	}

	@Override
	public void focusGained(FocusEvent e) {		
		for (JTextField field : new JTextField[] { nameField, expressionField, quantityField}) {
			if (e.getSource() == field && field.getForeground() == Color.GRAY) {
				field.setText("");
				field.setFont(FIELD_FILLED_FONT);
				field.setForeground(Color.BLACK);
			}
		}
		
		focusListener.focusGained(e);
	}

	@Override
	public void focusLost(FocusEvent e) {
		if (nameField == e.getSource()) {
			if (StringUtils.isEmpty(nameField.getText())) {
				resetNameField();
			} else {
				culture.setName(nameField.getText());
			}
		} else if (expressionField == e.getSource()) {
			if (StringUtils.isEmpty(expressionField.getText())) {
				resetExpressionField();
			} else {
				Pattern pattern = Pattern.compile("([A-Za-z][A-Za-z0-9]*) *= *(.+)$");
				Matcher matcher = pattern.matcher(expressionField.getText());
				
				if (matcher.find() && matcher.groupCount() == 2) {
					culture.setVariable(matcher.group(1));
					culture.setExpression(matcher.group(2));
					expressionField.setBackground(Color.WHITE);
				} else {
					expressionField.setBackground(Color.RED);
				}
			}
		} else if (quantityField == e.getSource()) {
			if (StringUtils.isEmpty(quantityField.getText())) {
				resetQuantityField();
			} else {
				try {
					double population = Double.parseDouble(quantityField.getText());
					
					culture.setPopulation(population);
					quantityField.setBackground(Color.WHITE);
				} catch (NumberFormatException e1) {
					quantityField.setBackground(Color.RED);
				}
			}
		}
		
		if (StringUtils.isNotBlank(culture.getName()) 
				&& StringUtils.isNotBlank(culture.getVariable())  
				&& StringUtils.isNotBlank(culture.getExpression())) {
			focusListener.focusLost(e);
		}
	}

}
