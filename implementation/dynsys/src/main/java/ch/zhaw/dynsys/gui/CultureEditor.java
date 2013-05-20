package ch.zhaw.dynsys.gui;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import ch.zhaw.dynsys.gui.models.Configuration;
import ch.zhaw.dynsys.gui.models.GraphProperty;
import ch.zhaw.dynsys.simulation.Culture;

public class CultureEditor extends JScrollPane {

	private static final long serialVersionUID = 1L;
	private final JPanel panel;
	private GraphPropertyEditor graphPropertyEditor;
	private List<Culture> cultures;

	public CultureEditor() {
		this.panel = new JPanel();
		this.cultures = new ArrayList<Culture>();

		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

		Dimension d = new Dimension(300, 100);
		getVerticalScrollBar().setUnitIncrement(d.height);

		viewport.add(panel);
		viewport.setPreferredSize(d);
		viewport.setMaximumSize(d);

		graphPropertyEditor = new GraphPropertyEditor(new GraphProperty(
				Double.NaN, Double.NaN, Double.NaN));
		panel.add(graphPropertyEditor);

		setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	}

	public void remove(CulturePropertyEditor propEditor) {
		cultures.remove(propEditor.getCulture());
		panel.remove(propEditor);
	}

	public void add(Culture culture) {
		cultures.add(culture);
		panel.add(new CulturePropertyEditor(this, culture));

	}

	public void set(Configuration configuration) {
		panel.removeAll();

		graphPropertyEditor = new GraphPropertyEditor(
				configuration.getGraphProperty());
		panel.add(graphPropertyEditor);

		List<Culture> cultures = configuration.getCultures();
		this.cultures.clear();
		for (Culture culture : cultures) {
			add(culture);
		}
		add(new Culture());
		revalidate();
		repaint();
	}

	public Configuration getConfiguration() {
		return new Configuration(graphPropertyEditor.getGraphProperty(),
				cultures);
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		getParent().revalidate();
		getParent().repaint();
		revalidate();
		repaint();
	}
}
