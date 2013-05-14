package ch.zhaw.dynsys.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.LineBorder;

import ch.zhaw.dynsys.simulation.Culture;

public class Legend extends JPanel {

	private static final long serialVersionUID = 1L;

	private List<Culture> cultures;
	private List<JTextField> fields;

	public Legend(List<Culture> cultures) {
		this.cultures = cultures;

		setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
		setBackground(Color.WHITE);
		setLayout(new SpringLayout());
		
		fields = new ArrayList<JTextField>(this.cultures.size());

		int i = 0;
		for (final Culture culture : cultures) {
			JLabel label = new JLabel(culture.getName());
			label.setHorizontalAlignment(JTextField.LEFT);
			label.setForeground(GraphPanel.COLOR_SEQUENCE[i++]);
			add(label);

			JTextField textField = new JTextField();
			textField.setPreferredSize(new Dimension(50, 16));
			add(textField);
			textField.setEditable(false);
			fields.add(textField);

			JButton minus = new LegendButton(culture, -1, new ImageIcon(getClass()
					.getClassLoader().getResource("minus.png")));
			JButton plus = new LegendButton(culture, 1, new ImageIcon(getClass()
					.getClassLoader().getResource("plus.png")));
			add(minus);
			add(plus);
		}

		SpringUtilities.makeCompactGrid(this, cultures.size(), 4, // rows, cols
				6, 6, // initX, initY
				6, 6);
	}

	public void update() {
		for (int i = 0; i < cultures.size(); i++) {
			Culture culture = cultures.get(i);
			JTextField field = fields.get(i);
			field.setText(NumberFormat.getInstance().format(culture.getValue()));
		}
	}
}
