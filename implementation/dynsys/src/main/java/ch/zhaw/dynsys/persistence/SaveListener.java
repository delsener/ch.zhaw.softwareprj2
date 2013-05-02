package ch.zhaw.dynsys.persistence;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;

import ch.zhaw.dynsys.gui.MessageHandler;
import ch.zhaw.dynsys.gui.SettingsPanel;

/**
 * Listener for the save action to display a file chooser dialog and save the
 * current cultures.
 */
public class SaveListener implements ActionListener {

	private final SettingsPanel settingsPanel;
	
	/**
	 * Constructor.
	 * 
	 * @param settingsPanel the settings panel.
	 */
	public SaveListener(SettingsPanel settingsPanel) {
		this.settingsPanel = settingsPanel;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setMultiSelectionEnabled(false);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setName("Choose a .dynsys file to store the cultures in:");
		fileChooser.setSelectedFile(new File(System.getProperty("user.home") + "/*.dynsys"));
		
		int result = fileChooser.showSaveDialog(null);
		
		if (JFileChooser.CANCEL_OPTION == result) {
			return;
		}
		
		File destinationFile = fileChooser.getSelectedFile();
		CulturePersistenceUtil.saveCultures(destinationFile, settingsPanel.getCultures());
		MessageHandler.displayInfoMessage("Successfully saved the current cultures!");
	}

}
