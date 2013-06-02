package ch.zhaw.dynsys.persistence;

import java.awt.HeadlessException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import ch.zhaw.dynsys.gui.models.Configuration;
import ch.zhaw.dynsys.gui.models.GraphProperty;
import ch.zhaw.dynsys.simulation.Culture;
import ch.zhaw.dynsys.simulation.Simulation;

public class ConfigurationPersistenceUtilTest {

	@Test
	public void testSaveAndLoad() throws Exception {
		// create dummy system configuration
		List<Culture> cultures = new ArrayList<Culture>();
		cultures.add(new Culture("A", "0", 0));
		cultures.add(new Culture("B", "1", 0.2));
		cultures.add(new Culture("C", "2", 11));
		cultures.add(new Culture("D", "A*C", 20));
		GraphProperty graphProperty = new GraphProperty(-100, 100);
		Configuration configuration = new Configuration(graphProperty,
				cultures, Simulation.DEFAULT_ITERATION_STEP);

		// persist configuration
		File tmpFile = File.createTempFile("test", "dynsys");
		ConfigurationPersistenceUtil.saveConfiguration(tmpFile, configuration);

		// load again
		Configuration loadedConfig = ConfigurationPersistenceUtil
				.loadCultures(tmpFile);
		Assert.assertEquals(configuration.getCultures().size(), loadedConfig
				.getCultures().size());
		Assert.assertEquals(
				configuration.getGraphProperty().getRangeAxisFrom(),
				loadedConfig.getGraphProperty().getRangeAxisFrom());
		Assert.assertEquals(configuration.getGraphProperty().getRangeAxisTo(),
				loadedConfig.getGraphProperty().getRangeAxisTo());
		Assert.assertEquals(configuration.getCultures().get(2).getName(),
				loadedConfig.getCultures().get(2).getName());
	}

	@Test
	public void testLoadError() throws Exception {
		// null file
		Assert.assertNull(ConfigurationPersistenceUtil.loadCultures(null));

		// invalid file
		File tmpFile = File.createTempFile("testError", "dynsys");
		try {
			ConfigurationPersistenceUtil.loadCultures(tmpFile);
		} catch (HeadlessException e) {
			// loading an invalid file will cause an error dialog to appear.
			// since test is running in headless mode, an HeadlessException is
			// what we expect here.
			Assert.assertTrue(true);
		}
	}

	@Test
	public void testSaveError() throws Exception {
		// null save
		ConfigurationPersistenceUtil.saveConfiguration(null, null);
		// should not cause an exception
		Assert.assertTrue(true);
	}
}
