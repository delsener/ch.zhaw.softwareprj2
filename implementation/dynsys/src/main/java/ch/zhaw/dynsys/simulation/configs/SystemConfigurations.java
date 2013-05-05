package ch.zhaw.dynsys.simulation.configs;

import ch.zhaw.dynsys.simulation.Culture;

public enum SystemConfigurations {

	BARM_SUGAR_SYSTEM("Hefe-Zucker System", new Culture[]{
//			new Culture("Temperatur (C°)", "t", "-math:sgn(t + 20) + H_diff", 50, false),
//			new Culture("Hefe", "H", "0.001 * H * Z * (20 - math:abs(50 - t))", 100, true),
//			new Culture("Zucker", "Z", "-math:max(0, H_diff)", 650, true)
			
			 new Culture("Temperatur (C°)", "t", "5", -20, false),
			 new Culture("Hefe", "H", "math:max(-H, 0.0001 * H * Z * (20 - math:abs(10 - t)) )", 100, true),
			 new Culture("Zucker", "Z", "math:min(0, -math:min(Z, H_diff))", 650, true)

	});
	
	private String name;
	private Culture[] cultures;
	
	/**
	 * Constructor.
	 * 
	 * @param name the names.
	 * @param cultures the cultures.
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
