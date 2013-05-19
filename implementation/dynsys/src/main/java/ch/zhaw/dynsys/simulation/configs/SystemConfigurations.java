package ch.zhaw.dynsys.simulation.configs;

import ch.zhaw.dynsys.simulation.Culture;

public enum SystemConfigurations {

	KINETICT_AFTER_MONOD_SYSTEM("Kinetik nach Monod", new Culture[] {
			new Culture("a", "0", 1),
			new Culture("b", "0", 1),
			new Culture("c", "0", 0),
			new Culture("d", "0", -0.2),
			new Culture("H Hefe",	"a * H * (Z / (b+Z))", 1),
			new Culture("Z Zucker", "Z > 0 ? c * H + d * H' : 0", 1) }),
			
	RABBIT_FOX_SYSTEM("Hase-Fuchs-System", new Culture[] {
			new Culture("K Karroten", "20 - 0.2 * h", 50),
			new Culture("H Hasen", "-1 - 1 * f + 1 * k", 100),
			new Culture("F Füchse", "-100 + 1 * h", 10) }),
	
	ELECTROMAGNETIC_SYSTEM("Elektromagnestische Schwingkreise", new Culture[] {
			new Culture("C", "0", 0.000001),
			new Culture("L", "0", 0.1),
			new Culture("R", "0", 30),
			new Culture("U", "0", 0.00000000000000001),
			new Culture("I Strom", "U / L - Q / (C * L) - R * I / L", 0),
			new Culture("Q Ladung", "I", 0)});

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
