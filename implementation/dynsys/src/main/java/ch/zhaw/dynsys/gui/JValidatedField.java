package ch.zhaw.dynsys.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

import org.apache.commons.lang3.StringUtils;

public class JValidatedField extends JTextField implements FocusListener {
	private static final long serialVersionUID = 1L;
	private static final Font FIELD_EMPTY_FONT = new Font("Arial", Font.ITALIC, 12);
	private static final Color FIELD_EMPTY_COLOR = Color.GRAY;
	private static final Font FIELD_FILLED_FONT = new Font("Arial", Font.PLAIN, 12);
	private static final Color FIELD_FILLED_COLOR = Color.BLACK;
	private static final Color FIELD_VALID_COLOR = Color.WHITE;
	private static final Color FIELD_INVALID_COLOR = Color.RED;

	private Validator validator;
	private String defaultValue;
	private String currentValue = null;

	public JValidatedField(int length, String defaultValue, String value, Validator validator) {
		super(length);
		this.validator = validator;
		this.defaultValue = defaultValue;
		
		addFocusListener(this);
		
		if (StringUtils.isEmpty(value)) {
			setText(defaultValue);
			setForeground(FIELD_EMPTY_COLOR);
			setFont(FIELD_EMPTY_FONT);
		} else {
			setText(value);
		}
	}

	/* 
	 * Clear value if default value is set.
	 */
	@Override
	public void focusGained(FocusEvent e) {
		if (getForeground() == FIELD_EMPTY_COLOR) {
			setText("");
			setFont(FIELD_FILLED_FONT);
			setForeground(FIELD_FILLED_COLOR);
		}
	}

	/* 
	 * If field is empty, then set default value.
	 */
	@Override
	public void focusLost(FocusEvent e) {
		if (StringUtils.isBlank(getText())) {
			if (StringUtils.isBlank(currentValue)) {
				setText(defaultValue);
				setForeground(FIELD_EMPTY_COLOR);
				setFont(FIELD_EMPTY_FONT);
			} else {
				setText(currentValue);
			}
			return;
		}
		
		
		if (validator.validate(getText())) {
			setBackground(FIELD_VALID_COLOR);
		} else {
			setBackground(FIELD_INVALID_COLOR);
		}

		currentValue = getText();
	}
	
	public static interface Validator {
		public boolean validate(String value);
	}
}
