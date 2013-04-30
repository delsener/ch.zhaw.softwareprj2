package ch.zhaw.dynsys.simulation;

public class Culture {

	private String name;
	private String variable;
	private String expression;
	private double population;
	private transient double growRate = 0; 
	
	
	public Culture() {
		super();
	}


	public Culture(String name, String variable, String expression, double population) {
		super();
		this.name = name;
		this.variable = variable;
		this.expression = expression;
		this.population = population;
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
	
	
}
