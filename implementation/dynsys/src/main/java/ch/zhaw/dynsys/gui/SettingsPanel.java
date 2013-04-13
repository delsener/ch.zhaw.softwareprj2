package ch.zhaw.dynsys.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class SettingsPanel extends JScrollPane {
	
	private static final long serialVersionUID = 1L;
	
	private JPanel parent;
	
	private List<CulturePanel> culturePanels = new LinkedList<CulturePanel>();
	private char currentCultureVariable = '@';

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
	    
	    addCulturePanel();
	    addCulturePanel();
	}
	
	public List<CulturePanel> getCulturePanels() {
		return culturePanels;
	}

	public void addCulturePanel() {
		addCulturePanel(null, null);
	}
	
	public void addCulturePanel(String initialName, String initialValue) {
		if (currentCultureVariable == 'Z') {
			return;
		}
		
		CulturePanel culturePanel = new CulturePanel(String.valueOf(++currentCultureVariable));
		culturePanel.setVariableDescription(initialName);
		culturePanel.setVariableValue(initialValue);
		
		culturePanels.add(culturePanel);
		parent.add(culturePanel, parent.getComponentCount() - 1);
		parent.revalidate();
	}
	
	private class AddListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			addCulturePanel();
		}
	}

}
