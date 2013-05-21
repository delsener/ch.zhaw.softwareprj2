package ch.zhaw.dynsys.gui;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import org.apache.commons.lang3.StringUtils;

import ch.zhaw.dynsys.gui.models.GraphProperty;

public class GraphPropertyEditor extends JPanel {

	private static final long serialVersionUID = 1L;
	private final GraphPanel graphPanel;
	private GraphProperty graphProperty;

	private JValidatedField fromField;
	private JValidatedField toField;

	public GraphPropertyEditor(final GraphPanel graphPanel,
			GraphProperty graphProperty) {
		this.graphProperty = graphProperty;
		this.graphPanel = graphPanel;
		this.graphPanel.configure(graphProperty);
		setOpaque(false);
		// setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
		// setMaximumSize(new Dimension(300, SystemUtils.IS_OS_MAC_OSX ? 60 :
		// 30));
		// setSize(100, 30);
		setLayout(new BorderLayout());

		JPanel items = new JPanel(new SpringLayout());
		items.setOpaque(false);

		// Range Axis From
		items.add(new JLabel("Range Axis: "));

		fromField = new JValidatedField(5, "Range Axis From",
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
							graphPanel
									.configure(GraphPropertyEditor.this.graphProperty);
						} catch (NumberFormatException numberFormatException) {
							return false;
						}

						return true;
					}
				});
		items.add(fromField);

		items.add(new JLabel(" - "));

		// Range Axis To
		toField = new JValidatedField(5, "Range Axis To",
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
							graphPanel
									.configure(GraphPropertyEditor.this.graphProperty);
						} catch (NumberFormatException numberFormatException) {
							return false;
						}

						return true;
					}
				});
		items.add(toField);

		add(items);

		SpringUtilities.makeCompactGrid(items, 1, 4, // rows, cols
				6, 6, // initX, initY
				6, 6);
	}

	public void setGraphProperty(GraphProperty graphProperty) {
		this.graphProperty = graphProperty;
		fromField.setText(Double.isNaN(graphProperty.getRangeAxisFrom()) ? null
				: String.valueOf(graphProperty.getRangeAxisFrom()));
		toField.setText(Double.isNaN(graphProperty.getRangeAxisTo()) ? null
				: String.valueOf(graphProperty.getRangeAxisTo()));
		graphPanel.configure(graphProperty);
	}

	public GraphProperty getGraphProperty() {
		return graphProperty;
	}
}
