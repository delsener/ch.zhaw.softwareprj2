package ch.zhaw.dynsys.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.SeriesRenderingOrder;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import ch.zhaw.dynsys.gui.models.GraphProperty;
import ch.zhaw.dynsys.simulation.Culture;

public class GraphPanel extends ChartPanel implements
		ch.zhaw.dynsys.simulation.Simulation.Listener {

	public static Color[] COLOR_SEQUENCE = new Color[] { Color.RED, Color.BLUE,
			Color.ORANGE, Color.PINK, Color.GREEN, Color.CYAN, Color.DARK_GRAY,
			Color.BLACK, Color.YELLOW };

	private static final int LASTEST_TIME_FRAME = 200;
	private static final long serialVersionUID = 1L;

	private XYSeriesCollection datasets = new XYSeriesCollection();
	private JFreeChart chart;
	private XYPlot plot;
	private ValueAxis domainAxis;

	private List<Culture> cultures;

	private double iteration = 0;

	private Legend legend;

	public GraphPanel() {
		super(null);

		setPreferredSize(new Dimension(800, 600));

		// chart
		chart = ChartFactory.createTimeSeriesChart(null, null, null, datasets,
				false, false, false);
		chart.setAntiAlias(true);

		// plot
		plot = (XYPlot) chart.getPlot();

		plot.setSeriesRenderingOrder(SeriesRenderingOrder.FORWARD);
		plot.setBackgroundPaint(Color.white);
		plot.setRangeGridlinePaint(Color.gray);
		plot.setDomainGridlinePaint(Color.gray);

		// range axis
		plot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);

		// domain axis
		domainAxis = (ValueAxis) plot.getDomainAxis();
		domainAxis.setVisible(false);

		setBorder(new SoftBevelBorder(BevelBorder.LOWERED));
		setChart(chart);

		setViewAll(false);
	}

	public void setViewAll(boolean flag) {
		if (flag) {
			domainAxis.setFixedAutoRange(0);
		} else {
			domainAxis.setFixedAutoRange(LASTEST_TIME_FRAME);
		}
	}

	public void clear() {
		datasets.removeAllSeries();
		iteration = 0;
	}

	@Override
	public void start(final List<Culture> cultures) {
		this.cultures = cultures;

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				clear();

				for (int i = 0; i < cultures.size(); i++) {
					Culture culture = cultures.get(i);
					XYSeries serie = new XYSeries(culture.getName());
					serie.add(iteration, culture.getValue());
					datasets.addSeries(serie);
					plot.getRenderer()
							.setSeriesStroke(i, new BasicStroke(2.0f));
					plot.getRenderer().setSeriesPaint(i, COLOR_SEQUENCE[i]);
					plot.getRenderer().setSeriesVisible(
							i,
							culture.getExpression() != null
									&& !"0".equals(culture.getExpression()
											.trim()));
				}

				JFrame frame = (JFrame) SwingUtilities
						.getWindowAncestor(GraphPanel.this);
				Container glassPane = (Container) frame.getGlassPane();
				glassPane.removeAll();

				legend = new Legend(GraphPanel.this.cultures);
				SpringLayout springLayout = new SpringLayout();
				glassPane.setLayout(springLayout);
				glassPane.add(legend);
				glassPane.setVisible(true);
				springLayout.putConstraint(SpringLayout.SOUTH, legend, 0,
						SpringLayout.SOUTH, GraphPanel.this);
				springLayout.putConstraint(SpringLayout.WEST, legend, 20,
						SpringLayout.WEST, GraphPanel.this);

				// update chart
				revalidate();
				repaint();

				iteration++;
			}
		});
	}

	@Override
	public void updated() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				for (int i = 0; i < cultures.size(); i++) {
					Culture culture = cultures.get(i);
					XYSeries dataset = datasets.getSeries(i);
					dataset.add(iteration, culture.getValue());
				}

				legend.update();

				// update chart
				revalidate();
				repaint();

				iteration++;
			}
		});
	}

	public void configure(GraphProperty graphProperty) {
		ValueAxis rangeAxis = plot.getRangeAxis(); // y-Axis
		rangeAxis.setRange(graphProperty.getRangeAxisFrom(),
				graphProperty.getRangeAxisTo());
	}
}
