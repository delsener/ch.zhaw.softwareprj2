package ch.zhaw.dynsys.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.apache.commons.lang3.StringUtils;

import ch.zhaw.dynsys.el.utils.ExpressionUtil;
import ch.zhaw.dynsys.simulation.Culture;
import ch.zhaw.dynsys.simulation.Simulation;

public class CulturePropertyEditor extends JPanel {

	private static final long serialVersionUID = 1L;
	private Culture culture;

	public CulturePropertyEditor(final Culture culture) {
		this.culture = culture;

		setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
		setMaximumSize(new Dimension(300, 100));
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
				addLastOne();
				
				Component button = (Component) e.getSource();
				Component propertyEditor = button.getParent();
				Container cultureEditor = (Container)propertyEditor.getParent();
				
				Simulation simulation = SimulationFactory.getInstance();
				List<Culture> cultures = simulation.getCultures();
				cultures.remove(CulturePropertyEditor.this.culture);
				SimulationFactory.newInstance(cultures);

				cultureEditor.remove(propertyEditor);
				cultureEditor.revalidate();
				cultureEditor.repaint();
				
			}
		});
		add(close, BorderLayout.NORTH);

		JPanel items = new JPanel(new SpringLayout());

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
							ExpressionUtil.evaluateExpressions(SimulationFactory.getInstance().getCultures(), 1);
							culture.setExpression(value);
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
		Container container = (Container)getParent();
		
		if (container.getComponent(container.getComponentCount() - 1) != this) {
			return;
		}
		
		container.add(new CulturePropertyEditor(new Culture()));
		container.revalidate();
		container.repaint();
	}
}
