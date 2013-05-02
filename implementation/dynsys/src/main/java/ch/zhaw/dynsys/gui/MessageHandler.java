package ch.zhaw.dynsys.gui;

import javax.swing.JOptionPane;

/**
 * The message handler provides functionality to display dialog messages.
 */
public final class MessageHandler {

	/**
	 * Displays an error message on a new frame.
	 * 
	 * @param message the error message to display.
	 */
	public static void displayErrorMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * Displays an warning message on a new frame.
	 * 
	 * @param message the warning message to display.
	 */
	public static void displayWarningMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "Warning", JOptionPane.WARNING_MESSAGE);
	}
	
	/**
	 * Displays an info message on a new frame.
	 * 
	 * @param message the info message to display.
	 */
	public static void displayInfoMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "Information", JOptionPane.INFORMATION_MESSAGE);
	}
}
