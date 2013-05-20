package ch.zhaw.dynsys.persistence;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import ch.zhaw.dynsys.gui.MessageHandler;
import ch.zhaw.dynsys.gui.models.Configuration;

/**
 * Utility class to store and load {@link Configuration} on/from the clients
 * file system.
 */
public final class ConfigurationPersistenceUtil {

	public static void saveConfiguration(File file, Configuration configuration) {
		if (file == null || configuration == null) {
			return;
		}
		
		checkFile(file);
		PersistenceUtil.saveObject(file, (Serializable) configuration);
	}

	public static Configuration loadCultures(File file) {
		if (file == null || !file.exists()) {
			return null;
		}
		return (Configuration) PersistenceUtil.loadObject(file);
	}

	private static void checkFile(File file) {
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
		} catch (IOException ex) {
			MessageHandler
					.displayErrorMessage("Could not create file, the following exception occured: "
							+ ex.getMessage());
			ex.printStackTrace();
		}
	}
}
