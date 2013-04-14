package ch.zhaw.dynsys.simulation;

public class Culture {

	private String name;
	private String variable;
	private String expression;
	private double quantity;
	
	
	public Culture() {
		super();
	}


	public Culture(String name, String variable, String expression, double quantity) {
		super();
		this.name = name;
		this.variable = variable;
		this.expression = expression;
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


	public double getQuantity() {
		return quantity;
	}


	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	
	
}
