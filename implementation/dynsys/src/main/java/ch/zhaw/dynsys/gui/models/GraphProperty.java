package ch.zhaw.dynsys.gui.models;

import java.io.Serializable;

/**
 * Model storing properties for the graph simulation.
 */
public class GraphProperty implements Serializable {

	/** Generated <code>serialVersionUID</code>. */
	private static final long serialVersionUID = 8040993589574861309L;

	private double rangeAxisFrom;
	private double rangeAxisTo;

	/**
	 * Constructor.
	 * 
	 * @param rangeAxisFrom
	 *            the minimum value of the range axis.
	 * @param rangeAxisTo
	 *            the maximum value of the range axis.
	 */
	public GraphProperty(double rangeAxisFrom, double rangeAxisTo) {
		this.rangeAxisFrom = rangeAxisFrom;
		this.rangeAxisTo = rangeAxisTo;
	}

	public double getRangeAxisFrom() {
		return rangeAxisFrom;
	}

	public double getRangeAxisTo() {
		return rangeAxisTo;
	}

	public void setRangeAxisFrom(double rangeAxisFrom) {
		this.rangeAxisFrom = rangeAxisFrom;
	}

	public void setRangeAxisTo(double rangeAxisTo) {
		this.rangeAxisTo = rangeAxisTo;
	}
}
