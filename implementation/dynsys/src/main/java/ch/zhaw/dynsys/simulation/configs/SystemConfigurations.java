package ch.zhaw.dynsys.simulation.configs;

import ch.zhaw.dynsys.simulation.Culture;

public enum SystemConfigurations {

	BARM_SUGAR_SYSTEM("Hefe-Zucker System", new Culture[] {
			new Culture("Temperatur (C°)", "-10 + 10 * h'", 0),
			new Culture("Hefe",	"0.0001 * h * z * (40 - abs(30 - t))", 100),
			new Culture("Zucker", "2*h'", 400) }),

	RABBIT_FOX_SYSTEM("Hasen-Fuchs System", new Culture[] {
			new Culture("Karroten", "20 - 0.2 * h", 50),
			new Culture("Hasen", "-1 - 1 * f + 1 * k", 100),
			new Culture("Füchse", "-100 + 1 * h", 10) }),
	
	ELECTROMAGNETIC_SYSTEM("Elektromagnestische Schwingkreise", new Culture[] {
			new Culture("H", "-f", 100),
			new Culture("F", "h", 1) });

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
