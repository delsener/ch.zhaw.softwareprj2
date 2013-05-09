package ch.zhaw.dynsys.simulation.configs;

import ch.zhaw.dynsys.simulation.Culture;

public enum SystemConfigurations {

	BARM_SUGAR_SYSTEM("Hefe-Zucker System", new Culture[] {
			new Culture(0, "Temperatur (C°)", "2", -20),
			new Culture(1, "Hefe",
					"max(-h, 0.00001 * h * z * (20 - abs(10 - t)) )", 100),
			new Culture(2, "Zucker", "-max(0, 2*h')", 400) }),

	RABBIT_FOX_SYSTEM("Hasen-Fuchs System", new Culture[] {
			new Culture(0, "Karroten", "2", -20),
			new Culture(1, "Hasen", "1 - 1 * f", 100),
			new Culture(2, "Füchse", "1 + 1 * h", 100) });

	private String name;
	private Culture[] cultures;

	/**
	 * Constructor.
	 * 
	 * @param name
	 *            the names.
	 * @param cultures
	 *            the cultures.
	 */
	private SystemConfigurations(String name, Culture[] cultures) {
		this.name = name;
		this.cultures = cultures;
	}

	public String getName() {
		return name;
	}

	public Culture[] getCultures() {
		return cultures;
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
