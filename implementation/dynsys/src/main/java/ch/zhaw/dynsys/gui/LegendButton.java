package ch.zhaw.dynsys.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.Timer;

import ch.zhaw.dynsys.simulation.Culture;

public class LegendButton extends JButton implements MouseListener {
	private static final long serialVersionUID = 1L;
	
	private Timer timer;
	private double increase;

	public LegendButton(final Culture culture, final double factor, Icon icon) {
		super(icon);
		setBackground(Color.WHITE);
		setBorderPainted(false);
		setPreferredSize(new Dimension(16, 16));
		addMouseListener(this);
		
		timer = new Timer(50, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				culture.setValue(culture.getValue() + factor * LegendButton.this.increase);
				LegendButton.this.increase++;
			}
		});
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		increase = 1;
		timer.start();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		timer.stop();
	}
}
