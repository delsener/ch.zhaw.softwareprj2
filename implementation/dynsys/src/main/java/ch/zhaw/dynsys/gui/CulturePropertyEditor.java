package ch.zhaw.dynsys.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;

import ch.zhaw.dynsys.el.utils.ExpressionUtil;
import ch.zhaw.dynsys.simulation.Culture;

public class CulturePropertyEditor extends JPanel {

	private static final long serialVersionUID = 1L;
	private CultureEditor editor;
	private Culture culture;

	public CulturePropertyEditor(final CultureEditor editor,
			final Culture culture) {
		this.editor = editor;
		this.culture = culture;

		setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
		setMaximumSize(new Dimension(300, SystemUtils.IS_OS_MAC_OSX ? 130 : 100));
		setAlignmentX(Component.LEFT_ALIGNMENT);
		setAlignmentY(Component.TOP_ALIGNMENT);

		setLayout(new BorderLayout());

		// close button
		JButton close = new JButton("x");
		close.setHorizontalAlignment(SwingConstants.RIGHT);
		close.setBorder(new EmptyBorder(0, 0, 0, 5));
		close.setPreferredSize(new Dimension(10, 10));
		close.setContentAreaFilled(false);
		close.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CultureEditor editor = CulturePropertyEditor.this.editor;
				editor.remove(CulturePropertyEditor.this);
				addLastOne();
				SimulationFactory.newInstance(editor.getConfiguration());

			}
		});
		add(close, BorderLayout.NORTH);

		JPanel items = new JPanel(new SpringLayout());

		if (editor.getComponent(0) == this) {
			items.add(new JLabel("Cultures and Variables"));
		}

		// name label
		items.add(new JValidatedField(50, "Name", culture.getName(),
				new JValidatedField.Validator() {
					@Override
					public boolean validate(String value) {
						addLastOne();

						if (StringUtils.isBlank(value)) {
							return false;
						}

						culture.setName(value);
						return true;
					}
				}));

		// quantity
		items.add(new JValidatedField(50, "Function", culture.getExpression(),
				new JValidatedField.Validator() {
					@Override
					public boolean validate(String value) {
						addLastOne();
						try {
							culture.setExpression(value);
							ExpressionUtil.evaluateExpressions(editor
									.getConfiguration().getCultures(), 1);
							return true;
						} catch (Exception e) {
							return false;
						}
					}
				}));

		// expression
		items.add(new JValidatedField(50, "Start value", Double.isNaN(culture
				.getPopulation()) ? null : String.valueOf(culture
				.getPopulation()), new JValidatedField.Validator() {
			@Override
			public boolean validate(String value) {
				addLastOne();

				try {
					culture.setPopulation(Double.parseDouble(value));
					return true;
				} catch (NumberFormatException e) {
					return false;
				}
			}
		}));

		add(items);

		SpringUtilities.makeCompactGrid(items, 3, 1, // rows, cols
				6, 6, // initX, initY
				6, 6);
	}

	public Culture getCulture() {
		return culture;
	}

	private void addLastOne() {
		if (editor.getComponent(editor.getComponentCount() - 1) != this) {
			return;
		}

		editor.add(new CulturePropertyEditor(editor, new Culture()));
	}
}
