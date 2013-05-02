package ch.zhaw.dynsys.persistence;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import ch.zhaw.dynsys.gui.MessageHandler;
import ch.zhaw.dynsys.gui.SettingsPanel;
import ch.zhaw.dynsys.simulation.Culture;

/**
 * Listener for the load action to load cultures from a file.
 */
public class LoadFromFileListener implements ActionListener {

	private final SettingsPanel settingsPanel;
	
	/**
	 * Constructor.
	 * 
	 * @param settingsPanel the settings panel.
	 */
	public LoadFromFileListener(SettingsPanel settingsPanel) {
		this.settingsPanel = settingsPanel;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("*.dynsys", "dynsys");
		fileChooser.setFileFilter(fileFilter);
		fileChooser.setMultiSelectionEnabled(false);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setName("Choose a .dynsys file to load the cultures from:");
		fileChooser.addChoosableFileFilter(fileFilter);
		fileChooser.setSelectedFile(new File(System.getProperty("user.home")));
		
		int result = fileChooser.showOpenDialog(null);
		
		if (JFileChooser.CANCEL_OPTION == result) {
			return;
		}
		
		File sourceFile = fileChooser.getSelectedFile();
		List<Culture> cultures = CulturePersistenceUtil.loadCultures(sourceFile);
		if (cultures == null || cultures.size() == 0) {
			MessageHandler.displayWarningMessage("The referenced filed dind't contain any culture!");
			return;
		}
		
		settingsPanel.resetSettingsPanel();
		settingsPanel.addCultures(cultures);
	}

}
