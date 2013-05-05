package ch.zhaw.dynsys.simulation;

import java.io.Serializable;

public class Culture implements Serializable {

	/** Generated <code>serialVersionUID</code>. */
	private static final long serialVersionUID = -3694941572400029800L;
	
	private String name;
	private String variable;
	private String expression;
	private double population;
	private boolean excintion;
	private transient double growRate = 0; 
	
	
	public Culture() {
		super();
	}


	public Culture(String name, String variable, String expression, double population, boolean excintion) {
		super();
		this.name = name;
		this.variable = variable;
		this.expression = expression;
		this.population = population;
		this.excintion = excintion;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getVariable() {
		return variable;
	}


	public void setVariable(String variable) {
		this.variable = variable;
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


	public double getGrowRate() {
		return growRate;
	}


	public void setGrowRate(double growRate) {
		this.growRate = growRate;
	}


	public boolean isExcintion() {
		return excintion;
	}


	public void setExcintion(boolean excintion) {
		this.excintion = excintion;
	}
	
	
}
