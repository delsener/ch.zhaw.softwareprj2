package ch.zhaw.dynsys.simulation.configs;

import ch.zhaw.dynsys.gui.models.Configuration;
import ch.zhaw.dynsys.gui.models.GraphProperty;
import ch.zhaw.dynsys.simulation.Culture;

public enum SystemConfigurations {

	KINETICT_AFTER_MONOD_SYSTEM("Kinetik nach Monod", new Configuration(
			new GraphProperty(-20d, 530d, 10d), new Culture[] {
					new Culture("a", "0", 1), new Culture("b", "0", 1),
					new Culture("c", "0", 0), new Culture("d", "0", -0.2),
					new Culture("H Hefe", "a * H * (Z / (b+Z))", 1),
					new Culture("Z Zucker", "Z > 0 ? c * H + d * H' : 0", 1) })),

	RABBIT_FOX_SYSTEM("Hase-Fuchs-System", new Configuration(new GraphProperty(
			-50d, 250d, 10d), new Culture[] {
			new Culture("K Karroten", "20 - 0.2 * h", 50),
			new Culture("H Hasen", "-1 - 1 * f + 1 * k", 100),
			new Culture("F Füchse", "-100 + 1 * h", 10) })),

	ELECTROMAGNETIC_SYSTEM("Elektromagnestische Schwingkreise",
			new Configuration(new GraphProperty(-1000d, 10000d, 10d),
					new Culture[] {
							new Culture("C", "0", 0.000001),
							new Culture("L", "0", 0.1),
							new Culture("R", "0", 30),
							new Culture("U", "0", 0.00000000000000001),
							new Culture("I Strom",
									"U / L - Q / (C * L) - R * I / L", 0),
							new Culture("Q Ladung", "I", 0) }));

	private String name;
	private Configuration configuration;

	/**
	 * Constructor.
	 * 
	 * @param name
	 *            the names.
	 * @param cultures
	 *            the cultures.
	 */
	private SystemConfigurations(String name, Configuration configuration) {
		this.name = name;
		this.configuration = configuration;
	}

	public String getName() {
		return name;
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	public static String[] names() {
		SystemConfigurations[] configs = values();
		String[] names = new String[configs.length];
		for (int i = 0; i < names.length; i++) {
			names[i] = configs[i].getName();
		}
		return names;
	}

	public static SystemConfigurations getByName(String name) {
		if (name == null) {
			return null;
		}

		for (SystemConfigurations conf : values()) {
			if (conf.getName().equals(name)) {
				return conf;
			}
		}
		return null;
	}
}
