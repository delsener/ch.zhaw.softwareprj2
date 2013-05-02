package ch.zhaw.dynsys.persistence;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

import ch.zhaw.dynsys.gui.MessageHandler;

/**
 * Utility class to store and load objects implementing {@link Serializable} on
 * the clients file system.
 */
public abstract class PersistenceUtil {

	public static void saveObject(File file, Serializable object) {
		// write serialized accounts to file
		try {
			OutputStream outputStream = new FileOutputStream(file);
			OutputStream buffer = new BufferedOutputStream(outputStream);
			ObjectOutput output = new ObjectOutputStream(buffer);
			try {
				output.writeObject(object);
			} finally {
				output.close();
			}
		} catch (IOException ex) {
			MessageHandler
					.displayErrorMessage("Error saving accounts. The follow exception occured: "
							+ ex.getMessage());
			ex.printStackTrace();
		}
	}

	public static Serializable loadObject(File file) {
		// read serialized accounts from file
		try {
			// use buffering
			InputStream inputStream = new FileInputStream(file);
			return PersistenceUtil.loadObject(inputStream);
		} catch (IOException ex) {
			MessageHandler
					.displayErrorMessage("Error loading accounts. The follow exception occured: "
							+ ex.getMessage());
			ex.printStackTrace();
		}
		return null;
	}

	public static Serializable loadObject(InputStream inputStream) {
		// read serialized accounts from file
		try {
			// use buffering
			InputStream buffer = new BufferedInputStream(inputStream);
			ObjectInput input = new ObjectInputStream(buffer);
			try {
				// deserialize the List
				return (Serializable) input.readObject();
			} finally {
				input.close();
			}
		} catch (ClassNotFoundException ex) {
			MessageHandler
			.displayErrorMessage("Error loading accounts. The follow exception occured: "
					+ ex.getMessage());
			ex.printStackTrace();
		} catch (IOException ex) {
			MessageHandler
			.displayErrorMessage("Error loading accounts. The follow exception occured: "
					+ ex.getMessage());
			ex.printStackTrace();
		}
		return null;
	}
}
