package ch.zhaw.dynsys.persistence;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.JOptionPane;

import ch.zhaw.dynsys.gui.CultureEditor;
import ch.zhaw.dynsys.gui.Legend;
import ch.zhaw.dynsys.gui.SimulationFactory;
import ch.zhaw.dynsys.simulation.Culture;
import ch.zhaw.dynsys.simulation.configs.SystemConfigurations;

/**
 * Listener for the load action to load cultures from a file.
 */
public class LoadFromPresetsListener implements ActionListener {

	private final CultureEditor settingsPanel;

	/**
	 * Constructor.
	 * 
	 * @param settingsPanel
	 *            the settings panel.
	 * @param graphValuesPanel
	 *            the {@link Legend}.
	 */
	public LoadFromPresetsListener(CultureEditor settingsPanel) {
		this.settingsPanel = settingsPanel;
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

		SimulationFactory.getInstance().stop();
		
		List<Culture> cultures = Arrays.asList(SystemConfigurations.getByName(result).getCultures());
		settingsPanel.set(cultures);
		settingsPanel.setVisible(true);
		
		SimulationFactory.newInstance(cultures);
	}
}
