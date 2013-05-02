package ch.zhaw.dynsys.persistence;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JOptionPane;

import ch.zhaw.dynsys.gui.MessageHandler;
import ch.zhaw.dynsys.gui.SettingsPanel;
import ch.zhaw.dynsys.simulation.configs.SystemConfigurations;

/**
 * Listener for the load action to load cultures from a file.
 */
public class LoadFromPresetsListener implements ActionListener {

	private final SettingsPanel settingsPanel;

	/**
	 * Constructor.
	 * 
	 * @param settingsPanel
	 *            the settings panel.
	 */
	public LoadFromPresetsListener(SettingsPanel settingsPanel) {
		this.settingsPanel = settingsPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String result = (String) JOptionPane
				.showInputDialog(null,
						"Select one of the preset system configurations",
						"Load preset configuration",
						JOptionPane.INFORMATION_MESSAGE, null,
						SystemConfigurations.names(),
						SystemConfigurations.BARM_SUGAR_SYSTEM.getName());


		if (result == null) {
			return;
		}

		settingsPanel.resetSettingsPanel();
		settingsPanel.addCultures(Arrays.asList(SystemConfigurations.getByName(result).getCultures()));
		MessageHandler
				.displayInfoMessage("Successfully loaded preset cultures '" + result + "'");
	}

}
