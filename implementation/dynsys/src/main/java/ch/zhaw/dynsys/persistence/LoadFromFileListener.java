package ch.zhaw.dynsys.persistence;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import ch.zhaw.dynsys.gui.CultureEditor;
import ch.zhaw.dynsys.gui.Legend;
import ch.zhaw.dynsys.gui.MessageHandler;
import ch.zhaw.dynsys.gui.SimulationFactory;
import ch.zhaw.dynsys.gui.models.Configuration;

/**
 * Listener for the load action to load cultures from a file.
 */
public class LoadFromFileListener implements ActionListener {

	private final CultureEditor settingsPanel;

	/**
	 * Constructor.
	 * 
	 * @param settingsPanel
	 *            the settings panel.
	 * @param graphValuesPanel
	 *            the {@link Legend}.
	 */
	public LoadFromFileListener(CultureEditor settingsPanel) {
		this.settingsPanel = settingsPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter fileFilter = new FileNameExtensionFilter(
				"*.dynsys", "dynsys");
		fileChooser.setFileFilter(fileFilter);
		fileChooser.setMultiSelectionEnabled(false);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setName("Choose a .dynsys file to load the cultures from:");
		fileChooser.addChoosableFileFilter(fileFilter);
		fileChooser.setSelectedFile(new File(System.getProperty("user.home")
				+ "/*"));

		int result = fileChooser.showOpenDialog(null);

		if (JFileChooser.CANCEL_OPTION == result) {
			return;
		}

		File sourceFile = fileChooser.getSelectedFile();
		Configuration configuration = ConfigurationPersistenceUtil
				.loadCultures(sourceFile);
		if (configuration == null) {
			MessageHandler
					.displayWarningMessage("The referenced filed dind't contain any configuration!");
			return;
		}

		SimulationFactory.getInstance().stop();

		settingsPanel.set(configuration);
		settingsPanel.setVisible(true);

		SimulationFactory.newInstance(configuration);

	}

}
