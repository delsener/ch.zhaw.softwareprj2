package ch.zhaw.dynsys.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import ch.zhaw.dynsys.simulation.Culture;
import ch.zhaw.dynsys.simulation.CultureListener;

public class SettingsPanel extends JScrollPane {
	
	private static final long serialVersionUID = 1L;
	
	private JPanel parent;
	
	private List<CulturePanel> culturePanels = new LinkedList<CulturePanel>();
	private List<CultureListener> cultureListeners = new ArrayList<CultureListener>();

	public SettingsPanel() {
		parent = new JPanel();
		parent.setLayout(new BoxLayout(parent, BoxLayout.PAGE_AXIS));		
		
		ActionListener addLister = new AddListener();
		
		JButton add = new JButton("+");
		add.setHorizontalAlignment(SwingConstants.RIGHT);
		add.setBorder(new EmptyBorder(5, 5, 5, 5));
		add.setContentAreaFilled(false);
		add.addActionListener(addLister);
		parent.add(add);
		
		Dimension d = new Dimension(300, 100);
		getVerticalScrollBar().setUnitIncrement(d.height);

		viewport.add(parent);
		viewport.setPreferredSize(d);
		
		setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	    setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	}
	
	public List<CulturePanel> getCulturePanels() {
		return culturePanels;
	}
	
	public List<Culture> getCultures() {
		if (culturePanels == null) {
			return new ArrayList<Culture>(0);
		}
		List<Culture> cultures = new ArrayList<Culture>();
		for (CulturePanel culturePanel : culturePanels) {
			if (culturePanel.getCulture() == null) {
				continue;
			}
			
			cultures.add(culturePanel.getCulture());
		}
		return cultures;
	}
	
	public void addCultures(List<Culture> cultures) {
		if (cultures == null) {
			return;
		}
		
		for (Culture culture : cultures) {
			addCulturePanel(culture);
		}
	}

	public void addCulturePanel(Culture culture) {
		CulturePanel culturePanel = new CulturePanel(culture, new CultureChangeListener(), new CloseListener());
		
		culturePanels.add(culturePanel);
		parent.add(culturePanel, parent.getComponentCount() - 1);
		parent.revalidate();
		
		if (culture == null) {
			return;
		}
		
		for (CultureListener listener : cultureListeners) {
			listener.changed(culture);
		}
	}
	

	public void resetSettingsPanel() {
		for (Culture culture : getCultures()) {
			removeCulture(culture);
		}
		
		Component[] components = parent.getComponents();
		for (Component component : components) {
			parent.remove(component);
		}
	}
	
	public void removeCulture(Culture culture) {
		culturePanels.remove(getCultures().indexOf(culture));
		for (CultureListener listener : cultureListeners) {
			listener.removed(culture);
		}
	}
	
	
	
	private class AddListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			addCulturePanel(null);
		}
	}
	
	
	public void addCultureListener(CultureListener listener) {
		cultureListeners.add(listener);
	}
	
	
	public void removeCultureListener(CultureListener listener) {
		cultureListeners.remove(listener);
	}
	
	private class CultureChangeListener implements FocusListener {		
		@Override
		public void focusGained(FocusEvent e) {}

		@Override
		public void focusLost(FocusEvent e) {
			JTextField field = (JTextField)e.getSource();
			CulturePanel culturePanel = (CulturePanel)field.getParent().getParent();
			
			for (CultureListener listener : cultureListeners) {
				listener.changed(culturePanel.getCulture());
			}
		}
	}
	
	
	private class CloseListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JComponent button = (JComponent)e.getSource();
			CulturePanel culturePanel = (CulturePanel)button.getParent();
			Container parent = culturePanel.getParent();			
			parent.remove(culturePanel);
			
			parent.revalidate();
			parent.repaint();
			
			removeCulture(culturePanel.getCulture());
		}
		
	}

}
