package ch.zhaw.dynsys.simulation;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

public class Culture implements Serializable {

	/** Generated <code>serialVersionUID</code>. */
	private static final long serialVersionUID = -3694941572400029800L;

	private String name = null;
	private String expression = null;
	private double population = Double.NaN;
	private transient double value = 0;
	private transient double growRate = 0;

	public Culture() {
		super();
	}

	public Culture(String name, String expression, double population) {
		this.name = name;
		this.expression = expression;
		this.population = population;
	}

	public Culture(Culture culture) {
		this.name = culture.getName();
		this.expression = culture.getExpression();
		this.population = culture.getPopulation();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVariable() {
		if (StringUtils.isEmpty(name)) {
			return null;
		}
		return name.substring(0, 1).toLowerCase();
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public double getPopulation() {
		return population;
	}

	public void setPopulation(double population) {
		this.population = population;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public double getGrowRate() {
		return growRate;
	}

	public void setGrowRate(double growRate) {
		this.growRate = growRate;
	}

	public boolean isValid() {
		return StringUtils.isNotBlank(name)
				&& StringUtils.isNotBlank(expression)
				&& !Double.isNaN(population);
	}
}
