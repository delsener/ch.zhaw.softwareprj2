package ch.zhaw.dynsys.persistence;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ch.zhaw.dynsys.gui.MessageHandler;
import ch.zhaw.dynsys.simulation.Culture;

/**
 * Utility class to store and load {@link Culture} on/from the clients file
 * system.
 */
public final class CulturePersistenceUtil {

	public static void saveCultures(File file, List<Culture> cultures) {
		checkFile(file);
		PersistenceUtil.saveObject(file, (Serializable) cultures);
	}

	@SuppressWarnings("unchecked")
	public static List<Culture> loadCultures(File file) {
		if (!file.exists()) {
			return new ArrayList<Culture>(0);
		}
		return (List<Culture>) PersistenceUtil.loadObject(file);
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
