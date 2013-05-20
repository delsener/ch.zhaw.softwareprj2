package ch.zhaw.dynsys.gui.models;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import ch.zhaw.dynsys.simulation.Culture;

/**
 * Configuration model used by the UI to initiate a simulation.
 */
public class Configuration implements Serializable {

	/** Generated <code>serialVersionUID</code>. */
	private static final long serialVersionUID = 8047734156444886382L;

	private final GraphProperty graphProperty;
	private final List<Culture> cultures;

	/**
	 * Constructor.
	 * 
	 * @param cultures
	 *            the cultures.
	 */
	public Configuration(List<Culture> cultures) {
		this.graphProperty = new GraphProperty(-1, -1, -1);
		this.cultures = cultures;
	}

	/**
	 * Constructor.
	 * 
	 * @param graphProperty
	 *            the graph properties model.
	 * @param cultures
	 *            the cultures.
	 */
	public Configuration(GraphProperty graphProperty, List<Culture> cultures) {
		this.graphProperty = graphProperty;
		this.cultures = cultures;
	}

	/**
	 * Constructor.
	 * 
	 * @param graphProperty
	 *            the graph properties model.
	 * @param cultures
	 *            the cultures.
	 */
	public Configuration(GraphProperty graphProperty, Culture[] cultures) {
		this.graphProperty = graphProperty;
		this.cultures = new LinkedList<Culture>();
		for (Culture culture : cultures) {
			this.cultures.add(culture);
		}
	}

	public GraphProperty getGraphProperty() {
		return graphProperty;
	}

	public List<Culture> getCultures() {
		return cultures;
	}
}
