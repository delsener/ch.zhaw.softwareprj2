package ch.zhaw.dynsys.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;

import ch.zhaw.dynsys.gui.models.GraphProperty;

public class GraphPropertyEditor extends JPanel {

	private static final long serialVersionUID = 1L;
	private final GraphProperty graphProperty;

	private JValidatedField fromField;
	private JValidatedField toField;

	public GraphPropertyEditor(GraphProperty graphProperty) {
		this.graphProperty = graphProperty;

		setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
		setMaximumSize(new Dimension(300, SystemUtils.IS_OS_MAC_OSX ? 110 : 80));
		setAlignmentX(Component.LEFT_ALIGNMENT);
		setAlignmentY(Component.TOP_ALIGNMENT);

		setLayout(new BorderLayout());

		JPanel items = new JPanel(new SpringLayout());

		items.add(new JLabel("Graph Settings"));

		// Range Axis From
		fromField = new JValidatedField(50, "Range Axis From",
				Double.isNaN(graphProperty.getRangeAxisFrom()) ? null : String
						.valueOf(graphProperty.getRangeAxisFrom()),
				new JValidatedField.Validator() {
					@Override
					public boolean validate(String value) {
						if (StringUtils.isBlank(value)) {
							return false;
						}

						try {
							double input = Double.parseDouble(value);
							GraphPropertyEditor.this.graphProperty
									.setRangeAxisFrom(input);
						} catch (NumberFormatException numberFormatException) {
							return false;
						}

						return true;
					}
				});
		items.add(fromField);

		// Range Axis To
		toField = new JValidatedField(50, "Range Axis To",
				Double.isNaN(graphProperty.getRangeAxisTo()) ? null : String
						.valueOf(graphProperty.getRangeAxisTo()),
				new JValidatedField.Validator() {
					@Override
					public boolean validate(String value) {
						if (StringUtils.isBlank(value)) {
							return false;
						}

						try {
							double input = Double.parseDouble(value);
							GraphPropertyEditor.this.graphProperty
									.setRangeAxisTo(input);
						} catch (NumberFormatException numberFormatException) {
							return false;
						}

						return true;
					}
				});
		items.add(toField);

		add(items);

		SpringUtilities.makeCompactGrid(items, 3, 1, // rows, cols
				6, 6, // initX, initY
				6, 6);
	}

	public GraphProperty getGraphProperty() {
		return graphProperty;
	}
}
