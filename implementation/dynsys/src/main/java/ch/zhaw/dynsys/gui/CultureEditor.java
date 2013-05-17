package ch.zhaw.dynsys.gui;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import ch.zhaw.dynsys.simulation.Culture;

public class CultureEditor extends JScrollPane {

	private static final long serialVersionUID = 1L;
	private final JPanel panel;
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

	public void set(List<Culture> cultures) {
		panel.removeAll();
		this.cultures.clear();
		for (Culture culture : cultures) {
			add(culture);
		}
		add(new Culture());
		revalidate();
		repaint();	
	}

	public List<Culture> getCultures() {
		return cultures;
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
