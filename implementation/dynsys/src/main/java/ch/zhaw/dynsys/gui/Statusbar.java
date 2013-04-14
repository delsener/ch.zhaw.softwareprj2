package ch.zhaw.dynsys.gui;

import java.awt.Dimension;
import java.util.Collection;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import ch.zhaw.dynsys.simulation.Culture;
import ch.zhaw.dynsys.simulation.SimulationListener;

public class Statusbar extends JPanel implements SimulationListener {
	private static final String READY = "Ready";
	private static final String RUNNING = "Running";

	private static final long serialVersionUID = 1L;
	
	private JLabel status = new JLabel(READY);

	public Statusbar() {
		setBorder(new BevelBorder(BevelBorder.LOWERED));
		setPreferredSize(new Dimension(0, 16));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		status.setHorizontalAlignment(SwingConstants.LEFT);
		add(status);
	}

	@Override
	public void started() {
		status.setText(RUNNING);		
	}

	@Override
	public void stoped() {
		status.setText(READY);	
	}

	@Override
	public void evolved(Collection<Culture> cultures) {}

	@Override
	public void clear() {}

}
