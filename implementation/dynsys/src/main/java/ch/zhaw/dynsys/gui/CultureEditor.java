package ch.zhaw.dynsys.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import ch.zhaw.dynsys.simulation.Culture;

public class CultureEditor extends JScrollPane {
	
	private static final long serialVersionUID = 1L;
	private final JPanel panel;
	private List<Culture> cultures;
	
	public CultureEditor() {		
		this.panel = new JPanel();
		this.cultures = new ArrayList<Culture>();
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		
		JButton add = new JButton("+");
		add.setHorizontalAlignment(SwingConstants.RIGHT);
		add.setBorder(new EmptyBorder(5, 5, 5, 5));
		add.setContentAreaFilled(false);
		add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				add(new Culture());
			}
		});
		panel.add(add);
		
		Dimension d = new Dimension(300, 100);
		getVerticalScrollBar().setUnitIncrement(d.height);

		viewport.add(panel);
		viewport.setPreferredSize(d);
		viewport.setMaximumSize(d);
		
		setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	    setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	}
	

	public void add(Culture culture) {
		cultures.add(culture);
		CulturePropertyEditor culturePanel = new CulturePropertyEditor(culture);
		ActionListener closeListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panel.remove((JComponent)e.getSource());
				panel.revalidate();
				panel.repaint();
			}
		};
		culturePanel.addCloseListener(closeListener);
		panel.add(culturePanel, panel.getComponentCount() - 1);
		panel.revalidate();
		panel.repaint();
	}
	
	
	public void addAll(List<Culture> cultures) {
		for (Culture culture : cultures) {
			add(culture);
		}
	}
	
	
	public void removeAll() {
		cultures.clear();
		for (int i = 0; i < panel.getComponentCount() - 1; i++) {
			panel.remove(i);
		}
	}
	
	
	public List<Culture> getCultures() {
		return cultures;
	}
	
	
	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		
		for (Component component : panel.getComponents()) {
			component.setEnabled(enabled);
		}
	}
}
