package ch.zhaw.dynsys.persistence;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JOptionPane;

import ch.zhaw.dynsys.gui.CultureEditor;
import ch.zhaw.dynsys.gui.GraphValuesPanel;
import ch.zhaw.dynsys.simulation.configs.SystemConfigurations;

/**
 * Listener for the load action to load cultures from a file.
 */
public class LoadFromPresetsListener implements ActionListener {

	private final CultureEditor settingsPanel;
	private final GraphValuesPanel graphValuesPanel;

	/**
	 * Constructor.
	 * 
	 * @param settingsPanel
	 *            the settings panel.
	 * @param graphValuesPanel
	 *            the {@link GraphValuesPanel}.
	 */
	public LoadFromPresetsListener(CultureEditor settingsPanel,
			GraphValuesPanel graphValuesPanel) {
		this.settingsPanel = settingsPanel;
		this.graphValuesPanel = graphValuesPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String result = (String) JOptionPane.showInputDialog(null,
				"Select one of the preset system configurations",
				"Load preset configuration", JOptionPane.INFORMATION_MESSAGE,
				null, SystemConfigurations.names(),
				SystemConfigurations.BARM_SUGAR_SYSTEM.getName());

		if (result == null) {
			return;
		}

		graphValuesPanel.reset();
		settingsPanel.removeAll();
		settingsPanel.addAll(Arrays.asList(SystemConfigurations.getByName(
				result).getCultures()));
	}

}
